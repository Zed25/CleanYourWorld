/*
 * Created by Umberto Ferracci from urania on 04/06/16 18.06
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.utlity.Message4Debug
 * File name: Message4Debug.java
 * Class name: Message4Debug
 * Last modified: 02/06/16 23.50
 */

package com.ufos.cyw16.cleanyourworld.utlity;

import android.util.Log;

/**
 * The type Message 4 debug.
 */
public class Message4Debug {
    private static boolean isDebug = true;
    private static String debuger = "urania";


    /**
     * Log.
     *
     * @param message the message
     */
    public static void log(String message) {
        if (isDebug) {
            Log.v(debuger, message);
        }
    }
}
