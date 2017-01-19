package com.example.karlmartin.herashop;

import android.graphics.Bitmap;

/**
 * Created by KarlMartin on 2017-01-19.
 */

public class Shop {
    public String shopName;
    public Bitmap icon;
    public double lat;
    public double lon;

    public Shop(String shopName, Bitmap icon, double lat, double lon) {
        this.shopName = shopName;
        this.icon = icon;
        this.lat = lat;
        this.lon = lon;
    }
}
