package com.example.axelle.travelbuddy;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
           public void onMarkerDragStart(Marker M0) {
           }

           @Override
           public void onMarkerDragEnd(Marker M0) {
               LatLng start = a.getPosition();
               LatLng end = b.getPosition();

               Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

               try {
                   List<Address> addresses = geocoder.getFromLocation(start.latitude, start.longitude, 1);
                   if (addresses.size() > 0) {
                       String display = "";

                       for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                           display += addresses.get(0).getAddressLine(i) + ", ";
                       }

                       TextView txtStart = (TextView) findViewById(R.id.ptA);
                       txtStart.setText(display);
                       Toast.makeText(getApplicationContext(), display, Toast.LENGTH_SHORT).show();
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               } finally {

               }

               try {
                   List<Address> addresses = geocoder.getFromLocation(end.latitude, end.longitude, 1);
                   if (addresses.size() > 0) {
                       String display = "";

                       for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                           display += addresses.get(0).getAddressLine(i) + ", ";
                       }

                       TextView txtStart = (TextView) findViewById(R.id.ptB);
                       txtStart.setText(display);
                       Toast.makeText(getApplicationContext(), display, Toast.LENGTH_SHORT).show();
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               } finally {

               }

           }

           @Override
           public void onMarkerDrag(Marker M0) {
           }

       });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);


        return super.onCreateOptionsMenu(menu);
    }


}