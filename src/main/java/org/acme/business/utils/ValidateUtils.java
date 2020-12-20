package org.acme.business.utils;

public class ValidateUtils {

    public static void validateNotNullParam(String objectName, Object object) throws Exception {
        if (object == null) {
            throw  new Exception("Does not exist" + objectName);
        }
    }

}
