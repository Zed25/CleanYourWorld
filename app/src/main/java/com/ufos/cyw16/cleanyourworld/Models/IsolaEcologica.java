/*
 * Created by Umberto Ferracci from simone_mancini and published on 15/06/16 12.39
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.IsolaEcologica
 * File name: IsolaEcologica.java
 * Class name: IsolaEcologica
 * Last modified: 15/06/16 12.39
 */

package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by simone_mancini on 15/06/16.
 */
public class IsolaEcologica {

    private int id, idComune;
    private String address, coordinates, description;

    public IsolaEcologica(int id, int idComune, String address, String coordinates, String description) {
        this.id = id;
        this.idComune = idComune;
        this.address = address;
        this.coordinates = coordinates;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdComune() {
        return idComune;
    }

    public void setIdComune(int idComune) {
        this.idComune = idComune;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
