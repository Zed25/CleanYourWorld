/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.Color
 * Last modified: 03/07/16 18.32
 */

package com.ufos.cyw16.cleanyourworld.model_new;

/**
 * The type Color.
 * It's an Entity class
 */
public class Color {

    private int idColors;
    private String name;
    private String colorCode;

    /**
     * Instantiates a new Color.
     */
    public Color() {
    }

    /**
     * Gets color code.
     *
     * @return the color code
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets color code.
     *
     * @param colorCode the color code
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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
     * Gets id colors.
     *
     * @return the id colors
     */
    public int getIdColors() {
        return idColors;
    }

    /**
     * Sets id colors.
     *
     * @param idColors the id colors
     */
    public void setIdColors(int idColors) {
        this.idColors = idColors;
    }
}