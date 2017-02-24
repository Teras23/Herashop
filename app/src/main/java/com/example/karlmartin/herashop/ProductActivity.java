package com.example.karlmartin.herashop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Log.i("ProductActivity", "Created activity");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        setTitle(bundle.getString("productName"));

        TextView productName = (TextView)(findViewById(R.id.productName));
        productName.setText(bundle.getString("productName"));

        TextView productDescription = (TextView)(findViewById(R.id.productDescription));
        productDescription.setText(bundle.getString("productDescription"));

        TextView productPrice = (TextView)(findViewById(R.id.productPrice));
        productPrice.setText(bundle.getDouble("productPrice") + "€");

        Bitmap bitmap;

        try {
            bitmap = BitmapFactory.decodeStream(getApplicationContext().openFileInput("tempImage"));

            ImageView imageView = (ImageView)findViewById(R.id.productImage);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e("ProductActivity", "Error loading temp image " + e.toString());
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
}
