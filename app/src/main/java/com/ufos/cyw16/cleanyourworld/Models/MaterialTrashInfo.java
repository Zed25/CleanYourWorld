/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.MaterialTrashInfo
 * Last modified: 03/07/16 18.13
 */

package com.ufos.cyw16.cleanyourworld.Models;

import android.graphics.Color;

/**
 * Created by simone_mancini on 03/07/16.
 */
public class MaterialTrashInfo {
    protected String day, thrash, colorOfTheTrash;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getThrash() {
        return thrash;
    }

    public void setThrash(String thrash) {
        this.thrash = thrash;
    }

    public int getColorOfTheTrash() {
        int color = Color.parseColor(colorOfTheTrash);
//        color = Color.parseColor("#ff0000");
        return color;
    }

    public void setColorOfTheTrash(String colorOfTheTrash) {
        this.colorOfTheTrash = colorOfTheTrash;
    }
}
