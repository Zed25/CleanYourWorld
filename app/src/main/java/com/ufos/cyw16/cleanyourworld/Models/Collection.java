/*
 * Created by Umberto Ferracci from simone_mancini and published on 15/06/16 12.50
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.Collection
 * File name: Collection.java
 * Class name: Collection
 * Last modified: 15/06/16 12.50
 */

package com.ufos.cyw16.cleanyourworld.Models;

import java.util.ArrayList;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class Collection {
    private int id, idComune, idDay;
    private ArrayList<Materials> materials;
    private String collectionType;
    private ArrayList<Colors> colors;

    public Collection(int id, int idComune, int idDay, ArrayList<Materials> materials, ArrayList<Colors> colors, String collectionType) {
        this.id = id;
        this.idComune = idComune;
        this.idDay = idDay;
        this.materials = materials;
        this.colors = colors;
        this.collectionType = collectionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", idComune=" + idComune +
                ", idDay=" + idDay +
                ", materials=" + materials +
                ", color='" + colors + '\'' +
                ", collectionType='" + collectionType + '\'' +
                '}';
    }

    public int getIdComune() {
        return idComune;
    }

    public void setIdComune(int idComune) {
        this.idComune = idComune;
    }

    public int getIdDay() {
        return idDay;
    }

    public void setIdDay(int idDay) {
        this.idDay = idDay;
    }

    public ArrayList<Materials> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<Materials> materials) {
        this.materials = materials;
    }

    public ArrayList<Colors> getColor() {
        return colors;
    }

    public void setColor(ArrayList<Colors> colors) {
        this.colors = colors;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }
}
