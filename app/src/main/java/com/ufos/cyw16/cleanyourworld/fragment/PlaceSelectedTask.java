/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedTask
 * Last modified: 04/07/16 15.33
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;

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


    /**
     * Instantiates a new Place selected task.
     *
     * @param latLng the lat lng
     */

    public PlaceSelectedTask(LatLng latLng) {
        this.latLng = latLng;
    }


    @Override
    protected String doInBackground(String... params) {
        PlaceSelectedParser placeSelectedParser = new PlaceSelectedParser();

        // Query parsing
        placeSelectedParser.parseXml(query);
        ArrayList<PlaceSelectedItem> placeSelectedItems = placeSelectedParser.getRadarPlaceSearchObjects();
        int sizeList = placeSelectedItems.size();
        for (int i = 0; i < sizeList; i++) {
            publishProgress((100 * i / sizeList), placeSelectedItems.get(i));
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
