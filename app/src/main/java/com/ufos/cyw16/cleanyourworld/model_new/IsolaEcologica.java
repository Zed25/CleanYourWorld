/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.IsolaEcologica
 * Last modified: 03/07/16 18.35
 */

package com.ufos.cyw16.cleanyourworld.model_new;

/**
 * The type Isola ecologica.
 * It's an Entity class
 */
public class IsolaEcologica {

    private int idIsolaEcologica;
    private String indirizzo;
    private String descrizione;
    private String cordinate;

    /**
     * Instantiates a new IsolaEcologica.
     */
    public IsolaEcologica() {
    }

    /**
     * Gets id isolaEcologica.
     *
     * @return the id isola ecologica
     */
    public int getIdIsolaEcologica() {
        return idIsolaEcologica;
    }

    /**
     * Sets id isolaEcologica.
     *
     * @param idIsolaEcologica the id isola ecologica
     */
    public void setIdIsolaEcologica(int idIsolaEcologica) {
        this.idIsolaEcologica = idIsolaEcologica;
    }

    /**
     * Gets indirizzo.
     *
     * @return the indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets indirizzo.
     *
     * @param indirizzo the indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Gets descrizione.
     *
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets descrizione.
     *
     * @param descrizione the descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Gets cordinate.
     *
     * @return the cordinate
     */
    public String getCordinate() {
        return cordinate;
    }

    /**
     * Sets cordinate.
     *
     * @param cordinate the cordinate
     */
    public void setCordinate(String cordinate) {
        this.cordinate = cordinate;
    }
}