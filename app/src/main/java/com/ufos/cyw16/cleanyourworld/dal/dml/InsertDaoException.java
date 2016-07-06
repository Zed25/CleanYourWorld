package com.ufos.cyw16.cleanyourworld.dal.dml;

/**
 * The type Insert dao exception.
 */
public class InsertDaoException extends Exception {

    /**
     * Instantiates a new Insert dao exception.
     */
    public InsertDaoException() {

    }

    /**
     * Instantiates a new Insert dao exception.
     *
     * @param message the message
     */
    public InsertDaoException(String message) {
        super("InsertDaoException: " + message);
    }

}
