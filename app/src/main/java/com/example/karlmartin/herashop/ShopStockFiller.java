package com.example.karlmartin.herashop;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by KarlMartin on 2017-01-29.
 */

public class ShopStockFiller extends AsyncTask<Void, Void, Stock[]> {

    private Shop shop;
    private ShopStockActivity activity;

    ShopStockFiller(Shop shop, ShopStockActivity activity) {
        this.shop = shop;
        this.activity = activity;
    }

    @Override
    protected Stock[] doInBackground(Void[] params) {
        Log.i("ShopMapFiller", "Reading JSON");

        JSONRequest request = new JSONRequest();

        try{
            JSONArray jsonArray = new JSONArray(request.getJSON("/stock/" + shop.typeId + "/" + shop.id + "/"));

            Log.i("ShopStockFiller", jsonArray.toString());

            Stock items[] = new Stock[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject djangoObject = jsonArray.getJSONObject(i);
                JSONObject stockData = djangoObject.getJSONObject("fields");

                String name = stockData.getString("name");
                int imageId = stockData.getInt("image");
                String description = stockData.getString("description");
                double price = stockData.getDouble("price");
                double discount = stockData.getDouble("discount");
                double discountPrice = stockData.getDouble("discountPrice");

                ImageRequest imageRequest = new ImageRequest();
                Bitmap image = imageRequest.getImage("/imageId/" + imageId + "/");

                items[i] = new Stock(name, image, description, price, discount, discountPrice);
            }

            return items;
        } catch (Exception e) {
            Log.e("ShopStockFiller", "Error reading JSON " + e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Stock[] result) {
        if(result != null) {
            activity.addStock(result);
        }
    }

}
