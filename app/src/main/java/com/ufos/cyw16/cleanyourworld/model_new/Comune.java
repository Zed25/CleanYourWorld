/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Comune
 * Last modified: 30/06/16 11.45
 */

package com.ufos.cyw16.cleanyourworld.model_new;

import java.util.List;

public class Comune {
    // FIXME: 02/07/16 addConstuctor
    private int idComune;
    private String name;
    private IsolaEcologica isolaEcologica;

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    private List<Collection> collections;

    public Comune() {
    }

    public int getIdComune() {
        return idComune;
    }

    public void setIdComune(int idComune) {
        this.idComune = idComune;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IsolaEcologica getIsolaEcologica() {
        return isolaEcologica;
    }

    public void setIsolaEcologica(IsolaEcologica isolaEcologica) {
        this.isolaEcologica = isolaEcologica;
    }


}