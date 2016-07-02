/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Regione
 * Last modified: 30/06/16 11.45
 */

package com.ufos.cyw16.cleanyourworld.model_new;


import java.util.List;

public class Regione {

    private int idRegione_int;
    private String name;
    private List<Provincia> province;

    public Regione() {
    }

    public int getIdRegione_int() {
        return idRegione_int;
    }

    public void setIdRegione_int(int idRegione_int) {
        this.idRegione_int = idRegione_int;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Provincia> getProvince() {
        return province;
    }

    public void setProvince(List<Provincia> province) {
        this.province = province;
    }

}