package com.easyconv.easyconvserver.core.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonUtil {

    public static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }
}
