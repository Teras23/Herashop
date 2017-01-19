package com.example.karlmartin.herashop;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

public class ShopMapActivityFiller extends AsyncTask<Void, Void, Shop[]> {

    private GoogleMap mMap;

    public ShopMapActivityFiller(GoogleMap mMap) {
        this.mMap = mMap;
    }

    @Override
    protected Shop[] doInBackground(Void[] params) {
        Log.i("ShopMapActivityFiller", "Reading JSON");

        JSONRequest request = new JSONRequest();

        try{
            JSONObject jsonReader = new JSONObject(request.getJSON("shops.json"));
            JSONArray jsonArray = jsonReader.getJSONArray("array");

            Shop shops[] = new Shop[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject shop = jsonArray.getJSONObject(i);

                String shopName = shop.getString("name");
                double lat = shop.getDouble("lat");
                double lng = shop.getDouble("lng");

                ImageRequest imageRequest = new ImageRequest();
                Bitmap image = imageRequest.getImage(shop.getString("img"));
                Bitmap bigImage = Bitmap.createScaledBitmap(image, 150, 150, false);

                shops[i] = new Shop(shopName, bigImage, lat, lng);

            }

            return shops;
        } catch (Exception e) {
            Log.e("ShopMapActivityFiller", "Error reading JSON " + e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Shop[] result) {
        if(result != null) {
            for (Shop shop : result) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(shop.lat, shop.lon))
                        .title(shop.shopName)
                        .icon(BitmapDescriptorFactory.fromBitmap(shop.icon)));
                Log.i("ShopMapActivityFiller", shop.shopName + " " + shop.lat + " " + shop.lon);
            }
        }
    }

}
