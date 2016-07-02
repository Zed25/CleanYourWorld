/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Provincia
 * Last modified: 30/06/16 11.45
 */

package com.ufos.cyw16.cleanyourworld.model_new;

import java.util.List;

public class Provincia {

    private int idProvincia;
    private String name;
    private List<Comune> comuni;

    public Provincia() {
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Comune> getComuni() {
        return comuni;
    }

    public void setComuni(List<Comune> comuni) {
        this.comuni = comuni;
    }

}