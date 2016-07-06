/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedTask
 * Last modified: 04/07/16 15.33
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The type Place selected task extends AsyncTask.
 * Recycling Place searching in radius km.
 */

public class PlaceSelectedTask extends AsyncTask<String, Object, String> {

    private final String googleAPIKey = "AIzaSyCd4ksb7VjC1IXtfJYAnnwAKP0FIRmdznE"; /* this key is different from android key */
    private final String googleLink = "https://maps.googleapis.com/maps/api/place/radarsearch/xml?";
    private final int radius = 15000;
    private final String keyword = "recycling";
    private LatLng latLng;
    private String query;
    private Context context;


    /**
     * Instantiates a new Place selected task.
     *
     * @param latLng the lat lng
     */

    public PlaceSelectedTask(LatLng latLng, Context context) {
        this.latLng = latLng;
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {
        PlaceSelectedParser placeSelectedParser = new PlaceSelectedParser();

        // Query parsing
        placeSelectedParser.parseXml(query);
        ArrayList<PlaceSelectedItem> placeSelectedItems = placeSelectedParser.getRadarPlaceSearchObjects();
        int sizeList = placeSelectedItems.size();
        for (int i = 0; i < sizeList; i++) {

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = null;
            String title = "";
            String snippet = "";
            try {
                addresses = geocoder.getFromLocation(placeSelectedItems.get(i).getLatLng().latitude, placeSelectedItems.get(i).getLatLng().longitude, 1);
                title += addresses.get(0).getLocality();
                for (int j = 0; j < addresses.get(0).getMaxAddressLineIndex(); j++) {
                    snippet += " " + addresses.get(0).getAddressLine(j);
                }

            } catch (IOException e) {
                Message4Debug.log(e.getMessage());
            }
            publishProgress((100 * i / sizeList), placeSelectedItems.get(i), title, snippet);
        }
        return null;
    }

    /**
     * Sets query (Google standards)
     */

    public void setQuery() {
        query = googleLink
                + "location=" + latLng.latitude + "," + latLng.longitude
                + "&radius=" + radius
                + "&type=" + keyword
                + "&keyword=" + keyword
                + "&key=" + googleAPIKey;
    }
}
