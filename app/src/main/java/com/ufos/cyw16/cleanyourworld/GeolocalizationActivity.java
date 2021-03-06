/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.GeolocalizationActivity
 * Last modified: 06/07/16 13.42
 */

/*
 * Created by UFOS from Sasha
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.GeolocalizationActivity
 * Last modified: 05/07/16 4.33
 */

package com.ufos.cyw16.cleanyourworld;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedItem;
import com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedTask;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Geolocalization activity.
 * Allows the users to look for the nearest garbage islands
 * using Google API's, in particular Maps API, Places API and Web Service API.
 */
public class GeolocalizationActivity extends FragmentActivity implements
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private final int USER_PLACE_PERMISSION = 40;
    private final int USER_GPS_PERMISSION = 50;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private GoogleApiClient client;
    private GoogleMap myGoogleMap;
    private ProgressBar progressBar;
    private Boolean permission;

    /**
     * Instantiates a new Geolocalization activity.
     */
    public GeolocalizationActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocalization);

        // Check GPS permissions
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, getResources().getString(R.string.strGPSAvailable), Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Map fragment declaration
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Adding Google API's
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

        // Autocomplete fragment declaration
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        // Autocomplete fragment listener
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {

                // When a Place is chosen, camera is moved on it and a marker is set on it
                LatLng selectedPlace = place.getLatLng();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, 13));
                googleMap.addMarker(new MarkerOptions().position(selectedPlace).title(place.getAddress().toString()));

                // Start AsyncTask
                placeSelectedTask(selectedPlace);
            }

            @Override
            public void onError(Status status) {
                Log.v("Error", status.toString());
            }
        });

        myGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                try {
                    setUpMap();
                } catch (Exception e) {
                    Message4Debug.log(e.getMessage());
                }
                return true;
            }
        });

        // Default configuration
        LatLng defaultLocation = new LatLng(41.893408, 12.482815);

        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            checkMapsPermission();
        } else {
            setPermission(true);
        }
        if (permission != null && permission) {
            try {
                setUpMap();
            } catch (Exception e) {
                myGoogleMap.setMyLocationEnabled(true);
                Message4Debug.log(e.getMessage());
            }
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 13));
        googleMap.addMarker(new MarkerOptions().position(defaultLocation));
        // Start AsyncTask
        placeSelectedTask(defaultLocation);

    }

    /**
     * Check maps permission.
     */
//runtime gps and localizzation permission request
    @TargetApi(Build.VERSION_CODES.M)
    private void checkMapsPermission() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add("GSM");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = getResources().getString(R.string.strNeedMultiplePermissions) + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setPermission(false);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
        setPermission(true);
    }

    /**
     * Show message ok cancel.
     *
     * @param message        the message
     * @param okListener     the ok listener
     * @param cancelListener the cancel listener
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok), okListener)
                .setNegativeButton(getResources().getString(R.string.cancel), cancelListener)
                .create()
                .show();
    }

    /**
     * Add permission boolean.
     *
     * @param permissionsList the permissions list
     * @param permission      the permission
     * @return the boolean
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    /**
     * Sets up map.
     *
     * @throws Exception the exception
     */
    private void setUpMap() throws Exception {
        // Enable MyLocation Layer of Google Map
        myGoogleMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Get Current Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkMapsPermission();
        }
        Location myLocation =  locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        myGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        myGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(getResources().getString(R.string.strYouAreHere)));
        placeSelectedTask(latLng);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    // All Permissions Granted
                    setPermission(true);
                    myGoogleMap.setMyLocationEnabled(true); //this is NOT an error beacause it requires permissions check, but this line is in an if block permission check


                } else {
                    // Permission Denied
                    setPermission(false);
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Place selected task.
     * Starts AsyncTask to find the nearest garbage islands.
     *
     * @param latLng the lat lng
     */
    public void placeSelectedTask(final LatLng latLng) {
        new PlaceSelectedTask(latLng, getBaseContext()) {

            @Override
            protected void onPreExecute() {

                // Clear the map and set progressBar visible
                myGoogleMap.clear();
                progressBar.setVisibility(View.VISIBLE);
                setQuery();
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Object... values) {
                super.onProgressUpdate(values);

                Integer progress = (Integer) values[0];
                progressBar.setIndeterminate(false);
                progressBar.setProgress(progress);
                // Add markers on garbage islands
                PlaceSelectedItem placeSelectedItem = (PlaceSelectedItem) values[1];
                String title = (String) values[2];
                String snippet = (String) values[3];
                myGoogleMap.addMarker(new MarkerOptions().position(placeSelectedItem.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.recyclemapicon)).title(title).snippet(snippet));

            }


            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.GONE);
                progressBar.setIndeterminate(true);
                super.onPostExecute(s);

            }

        }.execute();
    }

    /**
     * Show gps disabled alert to user.
     */
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.strGSPNotAvailable) + "?")
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.strYes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.strNo),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    public Boolean getPermission() {
        return permission;
    }

    /**
     * Sets permission.
     *
     * @param permission the permission
     */
    public void setPermission(Boolean permission) {
        this.permission = permission;
    }
}