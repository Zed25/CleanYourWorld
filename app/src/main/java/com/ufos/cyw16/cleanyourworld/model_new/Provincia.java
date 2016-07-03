/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Provincia
 * Last modified: 03/07/16 18.36
 */

package com.ufos.cyw16.cleanyourworld.model_new;

import java.util.List;

/**
 * The type Provincia.
 * It's an Entity class
 */
public class Provincia {
    // FIXME: 02/07/16 addConstuctor
    private int idProvincia;
    private String name;
    private List<Comune> comuni;

    /**
     * Instantiates a new Provincia.
     */
    public Provincia() {
    }

    /**
     * Gets id provincia.
     *
     * @return the id provincia
     */
    public int getIdProvincia() {
        return idProvincia;
    }

    /**
     * Sets id provincia.
     *
     * @param idProvincia the id provincia
     */
    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
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
     * Gets comuni.
     *
     * @return the comuni
     */
    public List<Comune> getComuni() {
        return comuni;
    }

    /**
     * Sets comuni.
     *
     * @param comuni the comuni
     */
    public void setComuni(List<Comune> comuni) {
        this.comuni = comuni;
    }

}