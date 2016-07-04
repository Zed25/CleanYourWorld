/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.MaterialTrashInfo
 * Last modified: 03/07/16 18.13
 */

package com.ufos.cyw16.cleanyourworld.Models;

import android.graphics.Color;

import com.ufos.cyw16.cleanyourworld.model_new.Product;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;

import java.util.List;

/**
 * Created by simone_mancini on 03/07/16.
 */
public class MaterialTrashInfo {
    protected String day, thrash, colorOfTheTrash;
    protected List<ProductType> productTypes;

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
        return color;
    }

    public void setColorOfTheTrash(String colorOfTheTrash) {
        this.colorOfTheTrash = colorOfTheTrash;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }
}
