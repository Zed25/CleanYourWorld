
/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Regione
 * Last modified: 03/07/16 18.31
 */

package com.ufos.cyw16.cleanyourworld.model_new;


import java.util.List;

/**
 * The type Regione.
 * It's an Entity class
 */
public class Regione {
    // FIXME: 02/07/16 add new constructor
    private int idRegione_int;
    private String name;
    private List<Provincia> province;

    /**
     * Instantiates a new Regione.
     */
    public Regione() {
    }

    /**
     * Gets id regione.
     *
     * @return the id regione int
     */
    public int getIdRegione_int() {
        return idRegione_int;
    }

    /**
     * Sets id regione.
     *
     * @param idRegione_int the id regione int
     */
    public void setIdRegione_int(int idRegione_int) {
        this.idRegione_int = idRegione_int;
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
     * Gets province.
     *
     * @return the province
     */
    public List<Provincia> getProvince() {
        return province;
    }

    /**
     * Sets province.
     *
     * @param province the province
     */
    public void setProvince(List<Provincia> province) {
        this.province = province;
    }

}