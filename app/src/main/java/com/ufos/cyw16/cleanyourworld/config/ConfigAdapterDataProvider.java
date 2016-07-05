/*
 * Created by Umberto Ferracci from ovidiudanielbarba and published on 6/23/16 5:47 PM
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.config.ConfigAdapterDataProvider
 * File name: ConfigAdapterDataProvider.java
 * Class name: ConfigAdapterDataProvider
 * Last modified: 6/23/16 5:47 PM
 */

package com.ufos.cyw16.cleanyourworld.config;

import android.widget.Button;

/**
 * Created by ovidiudanielbarba on 23/06/16.
 */
public class ConfigAdapterDataProvider {

    /* class used to fill view holder in recycler view*/
    private int id;
    private String name;
    // initially null, not null when there is a comune to fill a row
    // true if it has collection available in DB, false otherwise
    private Boolean hasCollection = null;

    public Boolean HasCollection() {
        return hasCollection;
    }

    public void setHasCollection(Boolean hasCollection) {
        this.hasCollection = hasCollection;
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
}
