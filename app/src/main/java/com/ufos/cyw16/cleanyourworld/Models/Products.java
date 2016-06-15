/*
 * Created by Umberto Ferracci from simone_mancini and published on 15/06/16 12.46
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.Products
 * File name: Products.java
 * Class name: Products
 * Last modified: 15/06/16 12.46
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class Products {
    private int id, ProductType;
    private String name, description, EAN;

    public Products(int id, int productType, String name, String description, String EAN) {
        this.id = id;
        ProductType = productType;
        this.name = name;
        this.description = description;
        this.EAN = EAN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductType() {
        return ProductType;
    }

    public void setProductType(int productType) {
        ProductType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }
}
