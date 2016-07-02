/*
 * Created by UFOS from Sasha
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedTask
 * Last modified: 7/2/16 12:07 PM
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Sasha on 02/07/16.
 */
public class PlaceSelectedTask extends AsyncTask<String, Object, String> {

    final private String googleAPIKey = "AIzaSyDRq_v69gnAuoTnCVTXaAa6GUdIOL1bH84";
    final private String googleLink = "https://maps.googleapis.com/maps/api/place/radarsearch/xml?";
    private LatLng latLng;
    private int radius;
    private String keyword;
    private String query;


    public PlaceSelectedTask(LatLng latLng, int radius, String keyword) {
        this.latLng = latLng;
        this.radius = radius;
        this.keyword = keyword;
    }


    @Override
    protected String doInBackground(String... params) {
        PlaceSelectedParser placeSelectedParser = new PlaceSelectedParser();
        placeSelectedParser.parseXml(query);
        ArrayList<PlaceSelectedItem> placeSelectedItems = placeSelectedParser.getRadarPlaceSearchObjects();
        int sizeList = placeSelectedItems.size();
        for (int i = 0; i < sizeList; i++) {
            publishProgress((100 * i / sizeList), placeSelectedItems.get(i));
        }
        return null;
    }

    public void setQuery() {
        query = googleLink
                + "location=" + latLng.latitude + "," + latLng.longitude
                + "&radius=" + radius
                + "&type=" + keyword
//                + "&name=" + s
                + "&keyword=" + keyword
                + "&key=" + googleAPIKey;
        Log.v("link", "query: " + query);
    }
}
