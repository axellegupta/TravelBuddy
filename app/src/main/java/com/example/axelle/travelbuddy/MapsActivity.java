package com.example.axelle.travelbuddy;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
        final Marker a = map.addMarker(new MarkerOptions()
                .position(A)
                .draggable(true)
                .title("START")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        final LatLng B = new LatLng(22.2780, 114.1740);
        final Marker b = map.addMarker(new MarkerOptions()
                .position(B)
                .draggable(true)
                .title("END")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        final Button ra = (Button) findViewById(R.id.ra);
        ra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
      //          a.setPosition();
            }
        });

        final Button rb = (Button) findViewById(R.id.rb);
        rb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
       //         b.setPosition();

            }
        });

       map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker M0) {  }

            @Override
            public void onMarkerDragEnd(Marker M0) {
                LatLng start = a.getPosition();
                LatLng end = b.getPosition();

                TextView txtStart = (TextView)findViewById(R.id.ptA);
                txtStart.setText(start.latitude + "," + start.longitude);
                Toast.makeText(getApplicationContext(), "START: "+start.latitude + "," + start.longitude, Toast.LENGTH_SHORT).show();

             TextView txtEnd = (TextView)findViewById(R.id.ptB);
             txtEnd.setText(end.latitude + "," + end.longitude);
                Toast.makeText(getApplicationContext(), "END: "+end.latitude + "," + end.longitude, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onMarkerDrag(Marker M0) {     }

        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        return super.onCreateOptionsMenu(menu);
    }
}