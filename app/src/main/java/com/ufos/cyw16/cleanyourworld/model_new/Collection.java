/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Collection
 * Last modified: 03/07/16 20.33
 */

package com.ufos.cyw16.cleanyourworld.model_new;

/**
 * The type Collection.
 * It's an Entity class
 */
public class Collection {

    private int idCollection;
    //    private Comune comune;
    private Day day;
    private CollectionType collectionType;
    private Material material;
    private Color color;

    /**
     * Instantiates a new Collection.
     */
    public Collection() {
    }

    /**
     * Gets id collection.
     *
     * @return the id collection
     */
    public int getIdCollection() {
        return idCollection;
    }

    /**
     * Sets id collection.
     *
     * @param idCollection the id collection
     */
    public void setIdCollection(int idCollection) {
        this.idCollection = idCollection;
    }

    /**
     * Gets day.
     *
     * @return the day
     */
    public Day getDay() {
        return day;
    }

    /**
     * Sets day.
     *
     * @param day the day
     */
    public void setDay(Day day) {
        this.day = day;
    }

    /**
     * Gets collectionType.
     *
     * @return the collection type
     */
    public CollectionType getCollectionType() {
        return collectionType;
    }

    /**
     * Sets collectionType.
     *
     * @param collectionType the collection type
     */
    public void setCollectionType(CollectionType collectionType) {
        this.collectionType = collectionType;
    }

    /**
     * Gets material.
     *
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets material.
     *
     * @param material the material
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }

}