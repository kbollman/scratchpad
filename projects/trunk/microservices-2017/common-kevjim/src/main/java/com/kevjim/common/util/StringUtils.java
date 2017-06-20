package com.kevjim.common.util;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * A Utility class for operating on Strings.
 */
public class StringUtils {

    public static String getCollectionAsCSV(Collection<?> collection) {
        StringBuilder csv = new StringBuilder();
        if (collection != null) {
            for (Object obj : collection) {
                if (csv.length() > 0) csv.append(",");
                csv.append(obj.toString());
            }
        }
        return csv.toString();
    }

    public static <T> T valueOfString(String string, Class<T> valueClass) {
        try {
            Method method = valueClass.getMethod("valueOf", String.class);
            return (T) method.invoke(null, string);
        } catch (Throwable t) {
            return null;
        }
    }
}
