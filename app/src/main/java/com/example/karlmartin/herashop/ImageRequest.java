package com.example.karlmartin.herashop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KarlMartin on 2017-01-19.
 */

public class ImageRequest {
    public Bitmap getImage(String path) {
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(Url.serverUrl + path);
            Log.i("ImageRequest", "Image Request to: " + url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Bitmap image = BitmapFactory.decodeStream(in);

            urlConnection.disconnect();
            return image;
        } catch(Exception e) {
            Log.e("ImageRequest", "Error getting image from server:\n" + e.toString());
        }
        return null;
    }
}
