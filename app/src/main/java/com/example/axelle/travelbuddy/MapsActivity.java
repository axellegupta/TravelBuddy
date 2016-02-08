package com.example.axelle.travelbuddy;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    public void onMapReady(final GoogleMap map) {
        // LatLng hk = new LatLng(22.2783, 114.1747);

        //map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(22.2783, 114.1747), 16));

        final LatLng defaultLocationA = new LatLng(22.2783, 114.1747);
        final Marker markerA = map.addMarker(new MarkerOptions()
                .position(defaultLocationA)
                .draggable(true)
                .title("START")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        final LatLng defaultLocationB = new LatLng(22.2780, 114.1740);
        final Marker markerB = map.addMarker(new MarkerOptions()
                .position(defaultLocationB)
                .draggable(true)
                .title("END")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        final PlaceAutocompleteFragment startAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.start_location);
        final PlaceAutocompleteFragment endAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.end_location);

        startAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());

                String placeDetailsStr = place.getName() + "\n"
                        + place.getId() + "\n"
                        + place.getLatLng().toString() + "\n"
                        + place.getAddress() + "\n"
                        + place.getAttributions();
//                txtPlaceDetails.setText(placeDetailsStr);

                markerA.setPosition(place.getLatLng());

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        endAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());

                String placeDetailsStr = place.getName() + "\n"
                        + place.getId() + "\n"
                        + place.getLatLng().toString() + "\n"
                        + place.getAddress() + "\n"
                        + place.getAttributions();
//                txtPlaceDetails.setText(placeDetailsStr);

                markerB.setPosition(place.getLatLng());

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker M0) {
            }

            @Override
            public void onMarkerDragEnd(Marker M0) {
                LatLng start = markerA.getPosition();
                LatLng end = markerB.getPosition();

                Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                try {
                    List<Address> addresses = geocoder.getFromLocation(start.latitude, start.longitude, 1);
                    if (addresses.size() > 0) {
                        String display = "";

                        for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                            display += addresses.get(0).getAddressLine(i) + ", ";
                        }

                        Toast.makeText(getApplicationContext(), display, Toast.LENGTH_SHORT).show();

                        startAutocompleteFragment.setText(display);

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

                        Toast.makeText(getApplicationContext(), display, Toast.LENGTH_SHORT).show();

                        endAutocompleteFragment.setText(display);
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

//        TextView txt = (TextView) findViewById(R.id.start);
    }

    public void onClick(View view) {

//        switch(view.getId()) {
//            case R.id.b1:
//                findPlace();
//                break;
//
//            case R.id.b2:
//                findPlace();
//                break;
//        }
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

//                TextView txt = (TextView) findViewById(R.id.start);
//                txt.setText(place.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i(TAG, status.getStatusMessage());
                // TODO: Handle the error.
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

//    PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//            getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//    autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//        @Override
//        public void onPlaceSelected(Place place) {
//            // TODO: Get info about the selected place.
//            Log.i(TAG, "Place: " + place.getName());
//
//            String placeDetailsStr = place.getName() + "\n"
//                    + place.getId() + "\n"
//                    + place.getLatLng().toString() + "\n"
//                    + place.getAddress() + "\n"
//                    + place.getAttributions();
//            txtPlaceDetails.setText(placeDetailsStr);
//        }
//
//        @Override
//        public void onError(Status status) {
//            // TODO: Handle the error.
//            Log.i(TAG, "An error occurred: " + status);
//        }
//    });
}