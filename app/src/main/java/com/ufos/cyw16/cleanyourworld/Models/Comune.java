/*
 * Created by Umberto Ferracci from ovidiudanielbarba and published on 6/15/16 11:53 AM
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.Comune
 * File name: Comune.java
 * Class name: Comune
 * Last modified: 6/15/16 11:53 AM
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by ovidiudanielbarba on 15/06/16.
 */
public class Comune {
    private String name;
    private int id;
    private int idProvincia;

    public Comune(String name, int id, int idProvincia) {
        this.name = name;
        this.id = id;
        this.idProvincia = idProvincia;
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

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }
}
