package com.example.karlmartin.herashop;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

public class ShopStockActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    private Shop shop = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setTitle(bundle.getString("shopName"));

        shop = bundle.getParcelable("shopClass");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getStock();
    }

    public void addStock(Stock[] stocks) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.activity_shop_stock, null);

        try {
            LinearLayout scrollLinearLayout = (LinearLayout) parent.findViewById(R.id.stock_scroll_linear_layout);

            for (Stock stock : stocks) {
                View view = inflater.inflate(R.layout.shop_stock, null);

                view.setTag(stock);

                TextView nameView = (TextView)view.findViewById(R.id.productName);
                nameView.setText(stock.itemName);

                TextView priceView = (TextView)view.findViewById(R.id.productPrice);
                priceView.setText(String.valueOf(stock.price) + "€");

                view.setOnClickListener(this);
                scrollLinearLayout.addView(view);

                Log.i("ShopStockFiller", stock.itemName);
            }

        } catch (Exception e) {
            Log.e("ShopStockActivity", "Could not create stock layouts " + e.toString());
        }

        setContentView(parent);
    }

    private void getStock() {
        ShopStockFiller shopStockFiller = new ShopStockFiller(shop, this);
        shopStockFiller.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_refresh) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Stock s = (Stock)view.getTag();
        Log.e("ShopStockActivity", "click on " + s.itemName);
    }
}
