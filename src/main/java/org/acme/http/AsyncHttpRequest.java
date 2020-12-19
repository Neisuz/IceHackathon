package org.acme.http;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AsyncHttpRequest extends HttpRequest {

    public void onFailure(IOException e) throws Exception {
        throw new Exception();
    }

    public void onResponse(HttpResponse httpResponse) {
        log.info("Async response: {} [{}]", httpResponse.getBody(), httpResponse.getStatus());
    }

}
