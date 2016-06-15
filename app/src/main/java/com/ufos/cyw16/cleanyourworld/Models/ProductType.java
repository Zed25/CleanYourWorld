/*
 * Created by Umberto Ferracci from simone_mancini and published on 15/06/16 12.43
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.ProductType
 * File name: ProductType.java
 * Class name: ProductType
 * Last modified: 15/06/16 12.43
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class ProductType {
    private int id, idMaterial;
    private String name;


    public ProductType(int id, int idMaterial, String name) {
        this.id = id;
        this.idMaterial = idMaterial;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
