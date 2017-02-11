package com.example.karlmartin.herashop;

import android.graphics.Bitmap;

/**
 * Created by KarlMartin on 2017-01-29.
 */

public class Stock {
    public String itemName;
    public Bitmap icon;
    public String description;
    public double price;
    public double discountPrice;
    public double discount;

    public Stock(String itemName, Bitmap icon, String description, double p, double d, double dp) {
        this.itemName = itemName;
        this.description = description;
        this.icon = icon;
        this.price = p;
        this.discount = d;
        this.discountPrice = dp;

    }
}
