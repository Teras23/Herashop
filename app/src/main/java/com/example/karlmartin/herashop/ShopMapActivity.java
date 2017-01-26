package com.example.karlmartin.herashop;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class ShopMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_map);

        try{
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCahceSize = 10 * 1024 * 1024; // 10MB
            HttpResponseCache.install(httpCacheDir, httpCahceSize);
        } catch (Exception e) {
            Log.e("ShopMapActivity", "Error creating http cache " + e.toString());
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void addShops() {
        ShopMapActivityFiller shopMapActivityFiller = new ShopMapActivityFiller(mMap);
        shopMapActivityFiller.execute();
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

        // Add a marker in Sydney and move the camera
        LatLng tallinn = new LatLng(59.437222, 24.745278);
        /*LatLng rademar = new LatLng(59.434222, 24.747278);
        LatLng sportland = new LatLng(59.439222, 24.743000);

        mMap.addMarker(new MarkerOptions()
                .position(rademar)
                .title("Rademar")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.rademar)));
        mMap.addMarker(new MarkerOptions()
                .position(sportland)
                .title("Sportland")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.sportland)));*/

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
        startActivity(intent);

        return true;
    }
}
