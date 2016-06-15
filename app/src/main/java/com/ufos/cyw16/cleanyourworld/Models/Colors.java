/*
 * Created by Umberto Ferracci from simone_mancini and published on 15/06/16 12.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.Colors
 * File name: Colors.java
 * Class name: Colors
 * Last modified: 15/06/16 12.48
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class Colors {
    private int id;
    private String color, colorCode;

    public Colors(int id, String color, String colorCode) {
        this.id = id;
        this.color = color;
        this.colorCode = colorCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
