
package com.ufos.cyw16.cleanyourworld.Models;

import java.util.List;

/**
 * Created by simone_mancini on 15/06/16.
 */
@Deprecated
public class Collection {
    private int id, idComune, idDay;
    private List<Materials> materials;
    //    private String collectionType;
    private List<Colors> colors;
    private List<CollectionType> collectionType;

    public Collection(int id, int idComune, int idDay, List<Materials> materials, List<Colors> colors, List<CollectionType> collectionType) {
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

    public List<CollectionType> getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(List<CollectionType> collectionType) {
        this.collectionType = collectionType;
    }
}
