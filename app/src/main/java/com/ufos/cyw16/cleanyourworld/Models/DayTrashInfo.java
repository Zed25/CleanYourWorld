package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 27/06/16.
 */
public class DayTrashInfo {
    protected String day, thrash, date;
    protected int colorOfTheDay;

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getThrash() {
        return thrash;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setThrash(String thrash) {
        this.thrash = thrash;
    }

    public int getColorOfTheDay() {
        return colorOfTheDay;
    }

    public void setColorOfTheDay(int colorOfTheDay) {
        this.colorOfTheDay = colorOfTheDay;
    }
}
