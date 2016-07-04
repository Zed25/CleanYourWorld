/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedTask
 * Last modified: 04/07/16 15.33
 */

/*
 * Created by UFOS from Sasha
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedTask
 * Last modified: 7/2/16 12:07 PM
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;

/**
 * Created by Sasha on 02/07/16.
 */
public class PlaceSelectedTask extends AsyncTask<String, Object, String> {

    final private String googleAPIKey = "AIzaSyCd4ksb7VjC1IXtfJYAnnwAKP0FIRmdznE"; /* this key is different from android key */
    final private String googleLink = "https://maps.googleapis.com/maps/api/place/radarsearch/xml?";
    private final int radius = 10000;
    private final String keyword = "recycling";
    private LatLng latLng;
    private String query;


    public PlaceSelectedTask(LatLng latLng) {
        this.latLng = latLng;
    }


    @Override
    protected String doInBackground(String... params) {
        Message4Debug.log("doInBackground()");
        PlaceSelectedParser placeSelectedParser = new PlaceSelectedParser();
        placeSelectedParser.parseXml(query);
        ArrayList<PlaceSelectedItem> placeSelectedItems = placeSelectedParser.getRadarPlaceSearchObjects();
        int sizeList = placeSelectedItems.size();
        for (int i = 0; i < sizeList; i++) {
            Message4Debug.log("publishProgress");
            publishProgress((100 * i / sizeList), placeSelectedItems.get(i));
        }
        return null;
    }

    public void setQuery() {
        query = googleLink
                + "location=" + latLng.latitude + "," + latLng.longitude
                + "&radius=" + radius
                + "&type=" + keyword
                + "&keyword=" + keyword
                + "&key=" + googleAPIKey;
        Message4Debug.log("link " + " query: " + query);
    }
}
