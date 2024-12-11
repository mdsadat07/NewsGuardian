package com.example.news_guardian;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiClient {

    private static final String BASE_URL = "https://content.guardianapis.com/";
    private static final String API_KEY = "4f732a4a-b27e-4ac7-9350-e9d0b11dd949";
    private static OkHttpClient client;

    public static OkHttpClient getInstance() {
        if (client == null) {
            client = new OkHttpClient.Builder().build();
        }
        return client;
    }

    public static String fetchArticles(String query) throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "search").newBuilder()
                .addQueryParameter("api-key", API_KEY)
                .addQueryParameter("q", query)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = getInstance().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected response code: " + response.code());
            }
        }
    }
}
