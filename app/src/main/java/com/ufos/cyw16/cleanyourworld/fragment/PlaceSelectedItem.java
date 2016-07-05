/*
 * Created by UFOS from Sasha
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedItem
 * Last modified: 7/2/16 12:51 PM
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import com.google.android.gms.maps.model.LatLng;

/**
 * The type Place selected item.
 */

public class PlaceSelectedItem {
    private String reference;
    private String id;
    private String place_id;
    private LatLng latLng;

    /**
     * Instantiates a new Place selected item.
     */
    public PlaceSelectedItem() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */

    public String getId() {
        return id;
    }

    /**
     * Gets lat lng.
     *
     * @return the lat lng
     */

    public LatLng getLatLng() {
        return latLng;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets lat lng.
     *
     * @param latLng the lat lng
     */

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    /**
     * Sets place id.
     *
     * @param place_id the place id
     */

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    /**
     * Sets reference.
     *
     * @param reference the reference
     */

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
