/*
 * Created by UFOS from ovidiudanielbarba
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.config.MaterialTypeEnum
 * Last modified: 7/3/16 1:34 PM
 */

package com.ufos.cyw16.cleanyourworld.config;

import org.w3c.dom.ProcessingInstruction;

/**
 * Created by ovidiudanielbarba on 03/07/16.
 */
public enum MaterialTypeEnum {
    GLASS(1),PAPER(2),PLASTIC(3),ORGANIC(4),METALS(5),NONRECYCLING(6),CENTERS(7),ELECTRONICS(8),BATTERIES(9),
    HOUSEWARE(10),OIL(11),GARDEN(12),CLOTHES(13),TONER(14),MEDICINALS(15);

    private int dbID;

    MaterialTypeEnum(int dbID) {
        this.dbID = dbID;
    }
}
