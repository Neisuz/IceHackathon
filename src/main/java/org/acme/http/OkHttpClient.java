package org.acme.http;

import io.quarkus.arc.Unremovable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jooq.lambda.tuple.Tuple2;

import javax.enterprise.context.ApplicationScoped;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Arrays;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@Unremovable
@ApplicationScoped
public class OkHttpClient implements AsyncHttpClient {

    private static final String METHOD_GET = "GET";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_OPTIONS = "OPTIONS";

    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    @ConfigProperty(name = "security.is_custom_truststore_required", defaultValue = "false")
    boolean isCustomTruststoreRequired;

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return prepareAndExecuteRequest(httpRequest, METHOD_GET);
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        return prepareAndExecuteRequest(httpRequest, METHOD_POST);
    }

    @Override
    public void postAsync(AsyncHttpRequest httpRequest) {
        prepareAndExecuteAsyncRequest(httpRequest, METHOD_POST);
    }

    @Override
    public HttpResponse put(HttpRequest httpRequest) {
        return prepareAndExecuteRequest(httpRequest, METHOD_PUT);
    }

    @Override
    public HttpResponse options(HttpRequest httpRequest) {
        return prepareAndExecuteRequest(httpRequest, METHOD_OPTIONS);
    }

    @Override
    public HttpResponse delete(HttpRequest httpRequest) {
        return prepareAndExecuteRequest(httpRequest, METHOD_DELETE);
    }

    @SneakyThrows
    private HttpResponse prepareAndExecuteRequest(HttpRequest httpRequest, String methodName) {
        Request okRequest = produceOkRequest(httpRequest, methodName);
        okhttp3.OkHttpClient httpClient = produceOkHttpClient(httpRequest, methodName);
        return execute(okRequest, httpClient);
    }

    private void prepareAndExecuteAsyncRequest(AsyncHttpRequest asyncHttpRequest, String methodName) {
        Request okRequest = produceOkRequest(asyncHttpRequest, methodName);
        okhttp3.OkHttpClient httpClient = produceOkHttpClient(asyncHttpRequest, methodName);
        executeAsync(okRequest, asyncHttpRequest, httpClient);
    }

    @SneakyThrows
    public static SSLSocketFactory getCustomSslSocketFactory(X509TrustManager trustManager) {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { trustManager }, null);
        return sslContext.getSocketFactory();
    }

    @SneakyThrows
    private okhttp3.OkHttpClient produceOkHttpClient(HttpRequest httpRequest, String methodName) {
        produceOkRequest(httpRequest, methodName);

        X509TrustManager customTrustManager = getCustomTrustManager();
        SSLSocketFactory customSslSocketFactory = getCustomSslSocketFactory(customTrustManager);

        okhttp3.OkHttpClient.Builder httpClientBuilder = new okhttp3.OkHttpClient().newBuilder()
                .readTimeout(httpRequest.getSocketTimeoutInSeconds(), SECONDS)
                .writeTimeout(httpRequest.getSocketTimeoutInSeconds(), SECONDS)
                .connectTimeout(httpRequest.getConnectionTimeoutInSeconds(), SECONDS);

        Tuple2<String, String> basicAuth = httpRequest.getBasicAuthCredentials();
        if (basicAuth != null) {
            httpClientBuilder.addInterceptor(new BasicAuthInterceptor(basicAuth.v1, basicAuth.v2));
        }

        if (isCustomTruststoreRequired) {
            log.debug("Using custom truststore...");
            httpClientBuilder.sslSocketFactory(customSslSocketFactory, customTrustManager);
        }

        httpClientBuilder.addInterceptor(chain -> {
            Request request = chain.request();
            log.info("Http client request {} {}\n{}{}", request.method(), request.url(), request.headers(), bodyToString(request));
            return chain.proceed(request);
        });

        return httpClientBuilder.build();
    }

    @SneakyThrows
    public static X509TrustManager getCustomTrustManager() {
        InputStream cacerts = loadResource("cacerts");
        KeyStore trusted = KeyStore.getInstance("JKS");
        trusted.load(cacerts, "changeit".toCharArray());
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trusted);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    public static InputStream loadResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    @SneakyThrows
    private String bodyToString(final Request request){
        Request copy = request.newBuilder().build();
        RequestBody body = copy.body();
        if (body == null) {
            return null;
        }
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }

    private Request produceOkRequest(HttpRequest httpRequest, String methodName) {
        Request.Builder requestBuilder = new Request.Builder();
        httpRequest.getHeaders().forEach(requestBuilder::header);

        HttpUrl httpUrl = HttpUrl.parse(httpRequest.getUrl());
        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        httpRequest.getParameters().forEach(urlBuilder::setQueryParameter);

        requestBuilder.url(urlBuilder.build());

        String body = httpRequest.getBody();
        if (isNotBlank(body)) {
            RequestBody requestBody = RequestBody.create(DEFAULT_MEDIA_TYPE, body);
            requestBuilder.method(methodName, requestBody);
        } else {
            RequestBody requestBody = RequestBody.create(null, new byte[0]);
            requestBuilder.method(methodName, requestBody);
            requestBuilder.header("Content-Length", "0");
        }

        return requestBuilder.build();
    }

    private HttpResponse execute(Request okRequest, okhttp3.OkHttpClient httpClient) throws IOException {
        try (Response okResponse = httpClient.newCall(okRequest).execute()) {
            return convertResponse(okResponse);
        }
    }

    private void executeAsync(Request okRequest, AsyncHttpRequest asyncHttpRequest, okhttp3.OkHttpClient httpClient) {
        httpClient.newCall(okRequest).enqueue(new Callback() {
            @SneakyThrows
            @Override
            public void onFailure(Call call, IOException e) {
                asyncHttpRequest.onFailure(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (Response res = response) {
                    HttpResponse httpResponse = convertResponse(res);
                    asyncHttpRequest.onResponse(httpResponse);
                }
            }
        });
    }

    private HttpResponse convertResponse(Response okResponse) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setBody(okResponse.body().string());
        httpResponse.setStatus(okResponse.code());
        httpResponse.setStatusText(okResponse.message());
        return httpResponse;
    }

}
