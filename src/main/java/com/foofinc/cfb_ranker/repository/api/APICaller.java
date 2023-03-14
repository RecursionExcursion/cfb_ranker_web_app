package com.foofinc.cfb_ranker.repository.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/*
Singleton
 */

enum APICaller {
    INSTANCE;

    String getJSONFromAPICall(String urlString, String bearer) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if(bearer != null){
            conn.setRequestProperty("Authorization", "Bearer " + bearer);
        }
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        conn.connect();

        int respCode = conn.getResponseCode();
        if (respCode != 200) {
            throw new RuntimeException("Http Response Code- " + respCode);
        } else {
            return new BufferedReader(new InputStreamReader(conn.getInputStream())).lines()
                                                                                   .collect(Collectors.joining());
        }
    }
}
