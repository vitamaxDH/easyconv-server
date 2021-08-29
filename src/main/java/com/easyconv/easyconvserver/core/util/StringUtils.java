package com.easyconv.easyconvserver.core.util;

public class StringUtils {

    public static boolean contains(String type, String... targets) {
        for (String target : targets) {
            if (type.contains(target)) {
                return true;
            }
        }
        return false;
    }

}
