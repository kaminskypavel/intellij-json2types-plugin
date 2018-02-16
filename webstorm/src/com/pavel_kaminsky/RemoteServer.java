package com.pavel_kaminsky;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;

public class RemoteServer {
    private static RemoteServer ourInstance = new RemoteServer();

    public static RemoteServer getInstance() {
        return ourInstance;
    }

    private RemoteServer() {
    }

    private static HttpRequest createHttpRequest(String input, String type) throws IOException {
        GenericUrl url = new GenericUrl("http://localhost:3000");
        HttpContent content = new UrlEncodedContent(ImmutableMap.of("json", input, "type", type));
        HttpRequestFactory requestFactory = new NetHttpTransport()
                .createRequestFactory();
        return requestFactory.buildPostRequest(url, content);
    }

    public static String fetch(String input, String type) {
        try {
            HttpRequest request = createHttpRequest(input, type);

            return request
                    .execute()
                    .parseAsString()
                    .replace("\"", "")
                    .replace("\\r\\n", "<br/>");
        } catch (Exception e) {
            return "Oh-Oh! Something went wrong";
        }
    }

}
