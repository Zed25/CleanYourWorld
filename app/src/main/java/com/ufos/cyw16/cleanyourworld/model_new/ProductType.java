/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.ProductType
 * Last modified: 03/07/16 18.36
 */

package com.ufos.cyw16.cleanyourworld.model_new;

import java.util.List;

/**
 * The type ProductType.
 * It's an Entity class
 */
public class ProductType {

    private int idProductType;
    private String name;
    private List<Product> products;
    private Material material;

    /**
     * Instantiates a new ProductType.
     */
    public ProductType() {
    }

    /**
     * Gets id productType.
     *
     * @return the id product type
     */
    public int getIdProductType() {
        return idProductType;
    }

    /**
     * Sets id productType.
     *
     * @param idProductType the id product type
     */
    public void setIdProductType(int idProductType) {
        this.idProductType = idProductType;
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
     * Gets products.
     *
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets products.
     *
     * @param products the products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
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

}