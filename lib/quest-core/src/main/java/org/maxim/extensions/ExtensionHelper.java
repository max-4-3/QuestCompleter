package org.maxim.extensions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExtensionHelper {

    public static String title(String string) {
        if (isStringEmpty(string))
            return string;

        StringBuilder sb = new StringBuilder();
        String[] chunks = string.trim().split("\\s+");

        for (int i = 0; i < chunks.length; ++i) {
            String chunk = chunks[i];

            if (chunk.isEmpty())
                continue;

            sb.append(Character.toUpperCase(chunk.charAt(0)));
            if (chunk.length() > 1) {
                sb.append(chunk.substring(1).toLowerCase());
            }

            if (i < chunks.length - 1) {
                sb.append(" ");
            }
        }

        return String.join(" ", sb);
    }

    /* Checks whether an item ( not array ) is in array */
    public static boolean isArrayContain(Object array, Object item) {
        if (isObjectNull(array) || isObjectNull(item))
            return false;

        if (item instanceof List<?> || item instanceof Object[])
            return false;

        if (array instanceof List<?>) {
            for (Object i : (List<?>) array) {
                if (Objects.equals(i, item))
                    return true;
            }
        }

        if (array instanceof Object[]) {
            for (Object i : (Object[]) array) {
                if (Objects.equals(i, item))
                    return true;
            }
        }

        return false;
    }

    public static boolean isStringEmpty(Object str) {
        return isObjectNull(str) || !(str instanceof String) || ((String) str).isBlank();
    }

    public static boolean isMapEmpty(Object map) {
        return isObjectNull(map) || !(map instanceof Map<?, ?>) || ((Map<?, ?>) map).size() == 0;
    }

    public static boolean isListEmpty(Object list) {
        return isObjectNull(list) || !(list instanceof List<?>) || ((List<?>) list).size() == 0;
    }

    public static boolean isArrayEmpty(Object array) {
        return isObjectNull(array) || !(array instanceof Object[]) || ((Object[]) array).length == 0;
    }

    public static boolean isObjectNull(Object obj) {
        return obj == null;
    }

}
