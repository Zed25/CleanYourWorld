
package com.ufos.cyw16.cleanyourworld.Models;

/**
 * Created by ovidiudanielbarba on 15/06/16.
 */
@Deprecated
public class Comune {
    private String name;
    private int id;
    private int idProvincia;

    public Comune(String name, int id, int idProvincia) {
        this.name = name;
        this.id = id;
        this.idProvincia = idProvincia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }
}
