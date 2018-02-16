package com.pavel_kaminsky;

import com.google.gson.*;

public class JSONUtils {
    private static JSONUtils ourInstance = new JSONUtils();

    public static JSONUtils getInstance() {
        return ourInstance;
    }

    private JSONUtils() {
    }

    public static String prettifyJSON(String uglyJSONString) throws JsonSyntaxException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        return gson.toJson(je);
    }

}
