package org.acme.http;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rsen on 3/17/16.
 */
@Setter
@Getter
public class HttpResponse {

    private static final int EFFECTIVE_STATUS_BAD_REQUEST = 4;
    private static final int EFFECTIVE_STATUS_BAD_GATEWAY = 5;

    private String body;
    private int status;
    private String statusText;

    public boolean isBadGatewayResponse() {
        return getEffectiveStatus() == EFFECTIVE_STATUS_BAD_GATEWAY;
    }

    public boolean isBadRequestResponse() {
        return getEffectiveStatus() == EFFECTIVE_STATUS_BAD_REQUEST;
    }

    private int getEffectiveStatus() {
        return status / 100;
    }

}
