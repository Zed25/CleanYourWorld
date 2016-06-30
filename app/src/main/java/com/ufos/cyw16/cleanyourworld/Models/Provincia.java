/*
 * Created by Umberto Ferracci from simone_mancini and published on 15/06/16 12.35
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.Provincia
 * File name: Provincia.java
 * Class name: Provincia
 * Last modified: 15/06/16 12.35
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class Provincia {
    private String name;
    private int id, idRegione;

    public Provincia(String name, int id, int idRegione) {
        this.name = name;
        this.id = id;
        this.idRegione = idRegione;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRegione() {
        return idRegione;
    }

    public void setIdRegione(int idRegione) {
        this.idRegione = idRegione;
    }
}
