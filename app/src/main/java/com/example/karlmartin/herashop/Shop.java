package com.example.karlmartin.herashop;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KarlMartin on 2017-01-19.
 */

public class Shop implements Parcelable {
    public int id;
    public int typeId;
    public String shopName;
    public Bitmap icon;
    public double lat;
    public double lon;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(typeId);
        out.writeString(shopName);
    }

    public static final Parcelable.Creator<Shop> CREATOR = new Parcelable.Creator<Shop>() {
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    private Shop(Parcel in) {
        id = in.readInt();
        typeId = in.readInt();
        shopName = in.readString();
        icon = null;
        lat = 0;
        lon = 0;
    }

    public Shop(int id, int typeId, String shopName, Bitmap icon, double lat, double lon) {
        this.id = id;
        this.typeId = typeId;
        this.shopName = shopName;
        this.icon = icon;
        this.lat = lat;
        this.lon = lon;
    }
}
