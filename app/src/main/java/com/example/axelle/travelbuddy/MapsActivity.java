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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
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

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private static final String TAG = "MapsActivity";

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

        final RadioGroup fromToGroup = (RadioGroup) findViewById(R.id.rbgroup);
        fromToGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup fromToGroup, int checkedId) {
                Toast.makeText(getApplicationContext(), "chk id: " + checkedId, Toast.LENGTH_SHORT).show();

                if (checkedId == R.id.ra) {
                    a.setPosition(new LatLng(22.3317272, 114.1600567)); // Shek Kip Mei MTR
                }
                else if (checkedId == R.id.rb) {
                    b.setPosition(new LatLng(22.3117281, 114.2147889)); // Kowloon Bay MTR
                }

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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            findPlace();
        }

        return super.onOptionsItemSelected(item);
    }

    public void findPlace() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i(TAG, status.getStatusMessage());
                // TODO: Handle the error.
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}