/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.ProductScan
 * Last modified: 05/07/16 17.46
 */

package com.ufos.cyw16.cleanyourworld.model_new;

/**
 * The type ProductScan.
 * It's an Entity class
 */
public class ProductScan {
    private int idScan;
    private Product product;
    private String date;

    public ProductScan(Product product, String date) {
        this.product = product;
        this.date = date;
    }

    public ProductScan() {
    }

    /**
     * Gets id scan.
     *
     * @return the id scan
     */
    public int getIdScan() {
        return idScan;
    }

    /**
     * Sets id scan.
     *
     * @param idScan the id scan
     */
    public void setIdScan(int idScan) {
        this.idScan = idScan;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }
}
