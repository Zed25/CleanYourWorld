/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapterException
 * File name: TableAdapterException.java
 * Class name: TableAdapterException
 * Last modified: 04/06/16 20.15
 */

package com.ufos.cyw16.cleanyourworld.dal.dml;

/**
 * The type Table adapter exception.
 */
public class TableAdapterException extends Exception {

    /**
     * Instantiates a new Table adapter exception.
     */
    public TableAdapterException() {

    }

    /**
     * Instantiates a new Table adapter exception.
     *
     * @param message the message
     */
    public TableAdapterException(String message) {
        super("TableAdapterException: " + message);
    }

}
