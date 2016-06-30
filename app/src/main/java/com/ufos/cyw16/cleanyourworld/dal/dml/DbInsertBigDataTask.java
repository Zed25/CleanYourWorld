/*
 * Created by Umberto Ferracci from urania and published on 17/06/16 7.44
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.DbInsertBigDataTask
 * File name: DbInsertBigDataTask.java
 * Class name: DbInsertBigDataTask
 * Last modified: 17/06/16 7.41
 */

package com.ufos.cyw16.cleanyourworld.dal.dml;

import android.os.AsyncTask;

import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;

/**
 * The type Db insert big data task.
 * @deprecated
 */
public class DbInsertBigDataTask extends AsyncTask<ArrayList<String[]>, String[], Void> {

    @Override
    protected Void doInBackground(ArrayList<String[]>... params) {
        Message4Debug.log("\n\t\tDbInsertBigDataTask.doInBackground().START = " + System.currentTimeMillis());
        long a = System.currentTimeMillis();
        String[] key = params[0].get(0);
        for (int i = 1; i < params[0].size(); i++) {
            publishProgress(key, params[0].get(i));
        }
        Message4Debug.log("hai popolato il database in " + (System.currentTimeMillis() - a) + " millisecondi");
        Message4Debug.log("\n\t\tDbInsertBigDataTask.doInBackground().STOP = " + System.currentTimeMillis());

        return null;
    }
}
