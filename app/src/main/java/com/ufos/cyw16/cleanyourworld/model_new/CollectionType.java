/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.CollectionType
 * Last modified: 03/07/16 18.32
 */

package com.ufos.cyw16.cleanyourworld.model_new;

/**
 * The type Collection type.
 * It's an Entity class
 */
public class CollectionType {

    private int idCollectionType;
    private String name;

    /**
     * Instantiates a new Collection type.
     */
    public CollectionType() {
    }

    /**
     * Gets id collectionType.
     *
     * @return the id collection type
     */
    public int getIdCollectionType() {
        return idCollectionType;
    }

    /**
     * Sets id collectionType.
     *
     * @param idCollectionType the id collection type
     */
    public void setIdCollectionType(int idCollectionType) {
        this.idCollectionType = idCollectionType;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }


}