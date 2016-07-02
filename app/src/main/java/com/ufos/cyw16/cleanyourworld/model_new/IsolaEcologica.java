/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.IsolaEcologica
 * Last modified: 30/06/16 11.45
 */

package com.ufos.cyw16.cleanyourworld.model_new;

public class IsolaEcologica {

    private int idIsolaEcologica;
    private String indirizzo;
    private String descrizione;
    private String cordinate;

    public IsolaEcologica() {
    }

    public int getIdIsolaEcologica() {
        return idIsolaEcologica;
    }

    public void setIdIsolaEcologica(int idIsolaEcologica) {
        this.idIsolaEcologica = idIsolaEcologica;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCordinate() {
        return cordinate;
    }

    public void setCordinate(String cordinate) {
        this.cordinate = cordinate;
    }
}