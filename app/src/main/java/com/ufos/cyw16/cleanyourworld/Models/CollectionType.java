/*
 * Created by Umberto Ferracci from simone_mancini and published on 15/06/16 12.45
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.CollectionType
 * File name: CollectionType.java
 * Class name: CollectionType
 * Last modified: 15/06/16 12.45
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class CollectionType {
    private int id;
    private String name;

    public CollectionType(int id, String name) {
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
