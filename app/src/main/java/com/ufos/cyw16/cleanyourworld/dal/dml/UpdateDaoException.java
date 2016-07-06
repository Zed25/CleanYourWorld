package com.ufos.cyw16.cleanyourworld.dal.dml;

/**
 * The type Update dao exception.
 */
public class UpdateDaoException extends Exception {

    /**
     * Instantiates a new Update dao exception.
     */
    public UpdateDaoException() {

    }

    /**
     * Instantiates a new Update dao exception.
     *
     * @param message the message
     */
    public UpdateDaoException(String message) {
        super("UpdateDaoException: " + message);
    }

}
