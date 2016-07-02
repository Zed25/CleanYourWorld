package com.ufos.cyw16.cleanyourworld.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ufos.cyw16.cleanyourworld.R;


/**
 * Created by Sasha on 11/05/16.
 */

public class GeolocalizationActivity extends FragmentActivity implements
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private GoogleApiClient client;
    private GoogleMap myGoogleMap;

    //TODO: insert toolbar

    public GeolocalizationActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocalization);


        //Map fragment declaration
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //adding Google API's
        client = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

    }

    @Override
    protected void onStart() {

        super.onStart();

        //Connecting to Google API's
        client.connect();
    }

    @Override
    protected void onStop() {

        //Disconnecting to Google API's
        client.disconnect();

        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        myGoogleMap = googleMap;

        //TODO: if gps !isActive() marker on COMUNE, else on current location (PROBLEM: LatLng???)
        //TODO: garb islands around (filtered) green markers

        //Autocomplete fragment declaration
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //Autocomplete fragment listener
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            //When place is chosen, camera is moved on it
            @Override
            public void onPlaceSelected(Place place) {
                LatLng selectedPlace = place.getLatLng();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, 15));
                googleMap.addMarker(new MarkerOptions().position(selectedPlace).title(place.getAddress().toString()));
                placeSelectedTask(selectedPlace, 2000, "recycling");
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });

        //Default start
        LatLng currentLocation = new LatLng(-33.867, 151.206);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here!"));
        placeSelectedTask(currentLocation, 2000, "recycling");

    }

    public void placeSelectedTask(final LatLng latLng, int radius, String keyword){
        new PlaceSelectedTask(latLng, radius, keyword){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                setQuery();
            }

            @Override
            protected void onProgressUpdate(Object... values) {
                super.onProgressUpdate(values);

                Integer progress = (Integer) values[0];

                PlaceSelectedItem placeSelectedItem = (PlaceSelectedItem) values[1];
                myGoogleMap.addMarker(new MarkerOptions().position(placeSelectedItem.getLatLng()));
            }
        }.execute();
    }
}