package com.example.karlmartin.herashop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.gms.vision.text.Line;

public class ShopStockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setTitle(bundle.getString("shopName"));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.activity_shop_stock, null);

        try {
            LinearLayout scrollLinearLayout = (LinearLayout) parent.findViewById(R.id.stock_scroll_linear_layout);

            for (int i = 0; i < 10; i++) {
                View view = inflater.inflate(R.layout.shop_stock, null);

                scrollLinearLayout.addView(view);
            }
        } catch (Exception e) {
            Log.e("ShopStockActivity", "Could not create stock layouts " + e.toString());
        }

        setContentView(parent);
    }
}
