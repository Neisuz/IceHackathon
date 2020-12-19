package org.acme.http;

import lombok.Getter;

/**
 * Created by rsen on 11/14/16.
 */
@Getter
//@Setter
public enum HttpStatus {

    OK(200),
    CREATED(201),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502);

    private int code;

    HttpStatus(int code) {
        this.code = code;
    }

}
