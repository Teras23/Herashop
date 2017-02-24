package com.example.karlmartin.herashop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class ShopStockActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    private Shop shop = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setTitle(bundle.getString("shopName"));

        shop = bundle.getParcelable("shopClass");

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

                ImageView imageView = (ImageView)view.findViewById(R.id.productImage);
                imageView.setImageBitmap(stock.icon);

                view.setOnClickListener(this);
                scrollLinearLayout.addView(view);

                Log.i("ShopStockFiller", stock.itemName);
            }

        } catch (Exception e) {
            Log.e("ShopStockActivity", "Could not create stock layouts " + e.toString());
        }

        setContentView(parent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        Stock stock = (Stock)view.getTag();
        Log.e("ShopStockActivity", "click on " + stock.itemName);

        Intent intent = new Intent(this, ProductActivity.class);

        intent.putExtra("productName", stock.itemName);
        intent.putExtra("productDescription", stock.description);
        intent.putExtra("productPrice", stock.price);

        //Saving the image to a temp file so it does not need to be requested from the server again
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            stock.icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput("tempImage", Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            Log.e("ShopStockActivity", "Error saving file " + e.toString());
        }

        startActivity(intent);
    }
}
