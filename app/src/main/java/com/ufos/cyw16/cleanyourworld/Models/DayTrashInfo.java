package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 27/06/16.
 */
public class DayTrashInfo {
    private String day, thrash;
    private int date;

    public int getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getThrash() {
        return thrash;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setThrash(String thrash) {
        this.thrash = thrash;
    }
}
