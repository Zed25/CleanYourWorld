/*
 * Created by Umberto Ferracci from urania and published on 07/06/16 5.17
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.DaoException
 * File name: DaoException.java
 * Class name: DaoException
 * Last modified: 07/06/16 2.25
 */

/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.DaoException
 * File name: DaoException.java
 * Class name: DaoException
 * Last modified: 04/06/16 20.15
 */

package com.ufos.cyw16.cleanyourworld.dal.dml;

/**
 * The type Dao exception.
 */
public class DaoException extends Exception {

    /**
     * Instantiates a new Dao exception.
     */
    public DaoException() {

    }

    /**
     * Instantiates a new Dao exception.
     *
     * @param message the message
     */
    public DaoException(String message) {
        super("DaoException: " + message);
    }

}
