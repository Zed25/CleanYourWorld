package com.ufos.cyw16.cleanyourworld.model_new;

import java.util.List;

public class Material {

    private String name;
    private int idMaterial;
    private List<ProductType> produtctTypes;

    public Material() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public List<ProductType> getProdutctTypes() {
        return produtctTypes;
    }

    public void setProdutctTypes(List<ProductType> produtctTypes) {
        this.produtctTypes = produtctTypes;
    }


}