package com.example.axelle.travelbuddy;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
      //  Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
       //  setSupportActionBar(myToolbar);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // LatLng hk = new LatLng(22.2783, 114.1747);

        //map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(22.2783, 114.1747), 16));

        // You can customize the marker image using images bundled with
        // your app, or dynamically generated bitmaps.
        final LatLng A = new LatLng(22.2783, 114.1747);
        Marker a = map.addMarker(new MarkerOptions()
                .position(A)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.common_plus_signin_btn_icon_dark_disabled))
                .draggable(true)
                .title("START")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        final LatLng B = new LatLng(22.2780, 114.1740);
        Marker b = map.addMarker(new MarkerOptions()
                .position(B)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.common_plus_signin_btn_icon_dark_disabled))
                .draggable(true)
                .title("END")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

       /* map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.common_plus_signin_btn_icon_dark_disabled))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(0, 0)));*/
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }
}