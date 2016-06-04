/*
 * Created by Umberto Ferracci from urania on 04/06/16 18.06
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapterException
 * File name: TableAdapterException.java
 * Class name: TableAdapterException
 * Last modified: 03/06/16 19.42
 */

/*
 * Created by Umberto Ferracci from urania on 03/06/16 17.29
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapterException
 * File name: TableAdapterException.java
 * Class name: TableAdapterException
 * Last modified: 03/06/16 17.29
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
