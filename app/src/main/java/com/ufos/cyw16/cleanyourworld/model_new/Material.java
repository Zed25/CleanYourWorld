/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Material
 * Last modified: 03/07/16 18.34
 */

package com.ufos.cyw16.cleanyourworld.model_new;

import java.util.List;

/**
 * The type Material.
 * It's an Entity class
 */
public class Material {

    private String name;
    private int idMaterial;
    private List<ProductType> produtctTypes;

    /**
     * Instantiates a new Material.
     */
    public Material() {
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

    /**
     * Gets id material.
     *
     * @return the id material
     */
    public int getIdMaterial() {
        return idMaterial;
    }

    /**
     * Sets id material.
     *
     * @param idMaterial the id material
     */
    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    /**
     * Gets produtctTypes.
     *
     * @return the produtct types
     */
    public List<ProductType> getProdutctTypes() {
        return produtctTypes;
    }

    /**
     * Sets produtctTypes.
     *
     * @param produtctTypes the produtct types
     */
    public void setProdutctTypes(List<ProductType> produtctTypes) {
        this.produtctTypes = produtctTypes;
    }


}