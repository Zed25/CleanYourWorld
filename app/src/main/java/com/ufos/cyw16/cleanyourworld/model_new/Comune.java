/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Comune
 * Last modified: 03/07/16 20.33
 */

package com.ufos.cyw16.cleanyourworld.model_new;

import java.util.List;

/**
 * The type Comune.
 * It's an Entity class
 */
public class Comune {
    // FIXME: 02/07/16 addConstuctor
    private int idComune;
    private String name;
    private IsolaEcologica isolaEcologica;
    private List<Collection> collections;

    /**
     * Instantiates a new Comune.
     */
    public Comune() {
    }

    /**
     * Gets collections.
     *
     * @return the collections
     */
    public List<Collection> getCollections() {
        return collections;
    }

    /**
     * Sets collections.
     *
     * @param collections the collections
     */
    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    /**
     * Gets id comune.
     *
     * @return the id comune
     */
    public int getIdComune() {
        return idComune;
    }

    /**
     * Sets id comune.
     *
     * @param idComune the id comune
     */
    public void setIdComune(int idComune) {
        this.idComune = idComune;
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
     * Gets isolaEcologica.
     *
     * @return the isola ecologica
     */
    public IsolaEcologica getIsolaEcologica() {
        return isolaEcologica;
    }

    /**
     * Sets isolaEcologica.
     *
     * @param isolaEcologica the isola ecologica
     */
    public void setIsolaEcologica(IsolaEcologica isolaEcologica) {
        this.isolaEcologica = isolaEcologica;
    }


}