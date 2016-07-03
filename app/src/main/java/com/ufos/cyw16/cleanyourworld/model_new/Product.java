/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Product
 * Last modified: 03/07/16 18.35
 */

package com.ufos.cyw16.cleanyourworld.model_new;

/**
 * The type Product.
 * It's an Entity class
 */
public class Product {

    private int idProduct;
    private String name;
    private String description;
    private String EAN;
    private ProductType productType;

    /**
     * Instantiates a new Product.
     */
    public Product() {
    }

    /**
     * Gets id product.
     *
     * @return the id product
     */
    public int getIdProduct() {
        return idProduct;
    }

    /**
     * Sets id product.
     *
     * @param idProduct the id product
     */
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets EAN.
     *
     * @return the ean
     */
    public String getEAN() {
        return EAN;
    }

    /**
     * Sets EAN.
     *
     * @param EAN the ean
     */
    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    /**
     * Gets product type.
     *
     * @return the product type
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * Sets product type.
     *
     * @param productType the product type
     */
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

}