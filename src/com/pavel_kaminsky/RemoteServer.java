package com.pavel_kaminsky;

import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public class RemoteServer {
    private static RemoteServer ourInstance = new RemoteServer();

    public static RemoteServer getInstance() {
        return ourInstance;
    }

    private RemoteServer() {
    }

    private static String formatTSURI(String input) throws URISyntaxException {
        URIBuilder ub = new URIBuilder("http://json2ts.com/Home/GetTypeScriptDefinition");
        ub.addParameter("code", input);
        ub.addParameter("ns", "someModule");
        ub.addParameter("root", "root");
        return ub.toString();
    }

    public static String fetch(String input) {
        try {
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory
                    .buildPostRequest(new GenericUrl(formatTSURI(input)), new EmptyContent());
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
