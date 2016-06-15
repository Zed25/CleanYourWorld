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

/**
 * Created by simone_mancini on 15/06/16.
 */
public class Collection {
    private int id, idComune, idMaterial, idDay, idColor, idCollectionType;
    //TODO enum days;

    public Collection(int id, int idComune, int idMaterial, int idDay, int idColor, int idCollectionType) {
        this.id = id;
        this.idComune = idComune;
        this.idMaterial = idMaterial;
        this.idDay = idDay;
        this.idColor = idColor;
        this.idCollectionType = idCollectionType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdComune() {
        return idComune;
    }

    public void setIdComune(int idComune) {
        this.idComune = idComune;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdDay() {
        return idDay;
    }

    public void setIdDay(int idDay) {
        this.idDay = idDay;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public int getIdCollectionType() {
        return idCollectionType;
    }

    public void setIdCollectionType(int idCollectionType) {
        this.idCollectionType = idCollectionType;
    }
}
