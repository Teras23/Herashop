package com.example.karlmartin.herashop;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KarlMartin on 2017-01-19.
 */

public class ServerConnection extends AsyncTask<GoogleMap, Void, String> {

    private GoogleMap mMap;

    public ServerConnection(GoogleMap mMap) {
        this.mMap = mMap;
    }

    private String getShopsString() {
        URL url;
        HttpURLConnection urlConnection = null;

        StringBuilder data = new StringBuilder();

        try {
            url = new URL("http://testing.eucolus.com/shops.json");

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = reader.readLine()) != null) {
                data.append(line);
            }

            urlConnection.disconnect();
        } catch(Exception e) {
            Log.e("ShopMapActivity", "Error getting data from server " + e.toString());
        }

        return data.toString();
    }

    @Override
    protected String doInBackground(GoogleMap[] params) {
        Log.i("ServerConnection", "Reading JSON");

        return getShopsString();
    }

    @Override
    protected void onPostExecute(String result) {
        try{
            JSONObject jsonReader = new JSONObject(result);
            JSONArray jsonArray = jsonReader.getJSONArray("array");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject shop = jsonArray.getJSONObject(i);
                String shopName = shop.getString("name");
                double lat = shop.getDouble("lat");
                double lng = shop.getDouble("lng");

                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(shopName));
                Log.i("ServerConnection", shopName + " " + lat + " " + lng);
            }

        } catch (Exception e) {
            Log.e("ServerConnection", "Error reading JSON " + e.toString());
        }
    }

}
