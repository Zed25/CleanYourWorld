/*
 * Created by Umberto Ferracci from ovidiudanielbarba and published on 6/23/16 6:18 PM
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.config.ConfigStep
 * File name: ConfigStep.java
 * Class name: ConfigStep
 * Last modified: 6/23/16 6:18 PM
 */

package com.ufos.cyw16.cleanyourworld.config;

/**
 * Created by ovidiudanielbarba on 23/06/16.
 */

/* identifies in which part of the configuration process you are in */
public enum ConfigStep {
    REGIONE(0),PROVINCIA(1),COMUNE(2),END(3);

    private int type;

    ConfigStep(int type) {
        this.type = type;
    }
}
