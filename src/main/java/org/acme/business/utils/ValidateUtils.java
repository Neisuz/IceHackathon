package org.acme.business.utils;

public class ValidateUtils {

    public static void validateNotNullParam(String objectName, Object object) {
        if (object == null) {
            System.out.println("Does not exist" + objectName);;
        }
    }
}
