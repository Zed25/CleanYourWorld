/*
 * Created by Umberto Ferracci from simone_mancini and published on 15/06/16 12.47
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.Materials
 * File name: Materials.java
 * Class name: Materials
 * Last modified: 15/06/16 12.47
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class Materials {
    private int id;
    private String  name;

    public Materials(int id, String name) {
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
