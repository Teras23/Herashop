package com.example.karlmartin.herashop;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.File;

public class ShopMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_map);

        try{
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10MB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (Exception e) {
            Log.e("ShopMapActivity", "Error creating http cache:\n" + e.toString());
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void addShops() {
        ShopMapFiller shopMapFiller = new ShopMapFiller(mMap);
        shopMapFiller.execute();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(this);
        mMap = googleMap;

        LatLng tallinn = new LatLng(59.437222, 24.745278);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(tallinn)
                .zoom(10)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setMaxZoomPreference(20);
        mMap.setMinZoomPreference(5);

        addShops();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Intent intent = new Intent(this, ShopStockActivity.class);
        intent.putExtra("shopName", marker.getTitle());
        intent.putExtra("shopClass", (Shop)marker.getTag());
        startActivity(intent);

        return true;
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
            mMap.clear();
            addShops();
        }

        return super.onOptionsItemSelected(item);
    }
}
