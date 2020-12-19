package org.acme.http;


import lombok.Getter;
import lombok.Setter;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsen on 3/17/16.
 */
@Getter
@Setter
public class HttpRequest {

    private String url;
    private String body;
    private int socketTimeoutInSeconds;
    private int connectionTimeoutInSeconds;
    private Map<String, String> parameters = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private Tuple2<String, String> basicAuthCredentials;

    /**
     * Sets only once both key and value are not null values. Otherwise - ignores.
     * @param key
     * @param value
     * @return
     */
    public Map<String, String> setParameter(String key, String value) {
        return setSafely(key, value, parameters);
    }

    public Map<String, String> setParameter(String key, Integer value) {
        return setSafely(key, String.valueOf(value), parameters);
    }

    public Map<String, String> setParameter(String key, Long value) {
        return setSafely(key, String.valueOf(value), parameters);
    }

    public Map<String, String> setParameter(String key, Boolean value) {
        return setSafely(key, String.valueOf(value), parameters);
    }

    public Map<String, String> setHeader(String key, String value) {
        return setSafely(key, value, headers);
    }

    protected String getFullUrl(String baseUrl, String path) {
        return String.format("%s%s", baseUrl, path);
    }

    private Map<String, String> setSafely(String key, String value, Map<String, String> map) {
        if (key != null && value != null) {
            map.put(key, value);
        }
        return map;
    }

}
