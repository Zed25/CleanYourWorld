/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.NotFoundElementException
 * Last modified: 06/07/16 10.45
 */

package com.ufos.cyw16.cleanyourworld;

/**
 * Created by simone_mancini on 06/07/16.
 */
public class NotFoundElementException extends Exception {
    /**
     * Instantiates a new NotFound exception.
     */
    public NotFoundElementException() {

    }

    /**
     * Instantiates a new NotFound exception.
     *
     * @param message the message
     */
    public NotFoundElementException(String message) {
        super("NotFoundException: " + message);
    }
}
