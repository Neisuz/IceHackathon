package org.acme.http;



public interface AsyncHttpClient extends HttpClient {

    void postAsync(AsyncHttpRequest httpRequest);

}
