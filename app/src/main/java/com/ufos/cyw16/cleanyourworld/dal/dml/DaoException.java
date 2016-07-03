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
