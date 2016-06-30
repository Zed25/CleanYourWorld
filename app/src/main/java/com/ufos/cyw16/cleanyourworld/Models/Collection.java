/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.Collection
 * Last modified: 30/06/16 10.34
 */

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

import java.util.List;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class Collection {
    private int id, idComune, idDay;
    private List<Materials> materials;
    private String collectionType;
    private List<Colors> colors;

    public Collection(int id, int idComune, int idDay, List<Materials> materials, List<Colors> colors, String collectionType) {
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

    public List<Materials> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Materials> materials) {
        this.materials = materials;
    }

    public List<Colors> getColor() {
        return colors;
    }

    public void setColor(List<Colors> colors) {
        this.colors = colors;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }
}
