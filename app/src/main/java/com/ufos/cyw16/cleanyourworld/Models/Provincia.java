

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 15/06/16.
 */
@Deprecated
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
