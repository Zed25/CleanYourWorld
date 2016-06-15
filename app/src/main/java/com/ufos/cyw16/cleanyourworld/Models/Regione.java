/*
 * Created by Umberto Ferracci from ovidiudanielbarba and published on 6/15/16 11:54 AM
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.Regione
 * File name: Regione.java
 * Class name: Regione
 * Last modified: 6/15/16 11:54 AM
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by ovidiudanielbarba on 15/06/16.
 */

public class Regione {
    private int id;
    private String name;

    public Regione(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
