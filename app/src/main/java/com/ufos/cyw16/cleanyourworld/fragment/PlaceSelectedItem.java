/*
 * Created by UFOS from Sasha
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedItem
 * Last modified: 7/2/16 12:51 PM
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sasha on 02/07/16.
 */
public class PlaceSelectedItem {
    private String reference;
    private String id;
    private String place_id;
    private LatLng latLng;

    public PlaceSelectedItem(String id, Double lat, Double lng, String place_id, String reference) {
        this.id = id;
        this.place_id = place_id;
        this.reference = reference;
        this.latLng = new LatLng(lat, lng);
    }

    public PlaceSelectedItem() {

    }

    public String getId() {
        return id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getReference() {
        return reference;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "RadarPlaceSearchItem:" +
                "\nid:\t" + id +
                "\tLatLng:\t" + latLng.toString() +
                "\nplace_id:\t" + place_id +
                "\nreference:\t" + reference;
    }

}
