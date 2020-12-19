package org.acme.http;

/**
 * Created by rsen on 3/17/16.
 */
public interface HttpClient {

    HttpResponse get(HttpRequest httpRequest);

    HttpResponse post(HttpRequest httpRequest);

    HttpResponse put(HttpRequest httpRequest);

    HttpResponse options(HttpRequest httpRequest);

    HttpResponse delete(HttpRequest httpRequest);

}
