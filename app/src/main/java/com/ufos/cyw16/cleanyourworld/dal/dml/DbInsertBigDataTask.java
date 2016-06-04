/*
 * Created by Umberto Ferracci from urania on 04/06/16 18.06
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.DbInsertBigDataTask
 * File name: DbInsertBigDataTask.java
 * Class name: DbInsertBigDataTask
 * Last modified: 04/06/16 17.12
 */

/*
 * Created by Umberto Ferracci from urania on 04/06/16 0.23
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.DbInsertBigDataTask
 * File name: DbInsertBigDataTask.java
 * Class name: DbInsertBigDataTask
 * Last modified: 04/06/16 0.23
 */

package com.ufos.cyw16.cleanyourworld.dal.dml;

import android.os.AsyncTask;

import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;

/**
 *
 */
public class DbInsertBigDataTask extends AsyncTask<ArrayList<String[]>, String[], Void> {


    public DbInsertBigDataTask() {
    }


    @Override
    protected Void doInBackground(ArrayList<String[]>... params) {
        long a = System.currentTimeMillis();
        String[] key = params[0].get(0);
        for (int i = 1; i < params[0].size(); i++) {
            publishProgress(key, params[0].get(i));
        }
        Message4Debug.log("hai popolato il database in " + (System.currentTimeMillis() - a) + " millisecondi");
        return null;
    }
}
