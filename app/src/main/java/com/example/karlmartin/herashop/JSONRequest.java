package com.example.karlmartin.herashop;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KarlMartin on 2017-01-19.
 */

public class JSONRequest {
    public String getJSON(String path) {
        URL url;
        HttpURLConnection urlConnection;

        StringBuilder data = new StringBuilder();

        try {
            url = new URL(Url.serverUrl + path);
            Log.i("JSONRequest", "JSON Request to: " + url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("Cache-Control", "no-cache");
            InputStream in = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = reader.readLine()) != null) {
                data.append(line);
            }

            urlConnection.disconnect();
        } catch(Exception e) {
            Log.e("JSONRequest", "Error getting json from server:\n" + e.toString());
        }

        return data.toString();
    }
}
