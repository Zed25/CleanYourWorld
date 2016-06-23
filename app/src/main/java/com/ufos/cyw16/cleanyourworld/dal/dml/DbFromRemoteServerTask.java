/*
 * Created by Umberto Ferracci from urania and published on 23/06/16 17.49
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.DbFromRemoteServerTask
 * File name: DbFromRemoteServerTask.java
 * Class name: DbFromRemoteServerTask
 * Last modified: 23/06/16 17.34
 */

/*
 * Created by Umberto Ferracci from urania and published on 17/06/16 7.44
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.DbFromRemoteServerTask
 * File name: DbFromRemoteServerTask.java
 * Class name: DbFromRemoteServerTask
 * Last modified: 17/06/16 7.41
 */

package com.ufos.cyw16.cleanyourworld.dal.dml;

import android.os.AsyncTask;

import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type Db from remote server task.
 * @deprecated
 */
public class DbFromRemoteServerTask extends AsyncTask<String, String[], ArrayList<String[]>> {

    @Override

    protected ArrayList<String[]> doInBackground(String... params) {
        Message4Debug.log("\n\t\tDbFromRemoteServerTask.doInBackground().START = " + System.currentTimeMillis());
        long a = System.currentTimeMillis();
        ArrayList<String[]> list = new ArrayList<>();
        String line = "";
        String query = params[0];
        String csvSeparator = params[1];
        try {
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int g = 0;
            while ((line = br.readLine()) != null) {
                if (g > 500)
                    break;
                g++;
                String[] row = line.split(csvSeparator);
                for (int i = 0; i < row.length; i++) {
                    row[i] = row[i].replace("\"", "");
                }
                list.add(row);
            }
        } catch (FileNotFoundException e) {
            Message4Debug.log(e.toString() + "\n" + e.getMessage());
        } catch (MalformedURLException e) {
            Message4Debug.log(e.toString() + "\n" + e.getMessage());
        } catch (IOException e) {
            Message4Debug.log(e.toString() + "\n" + e.getMessage());
        }// TODO: 31/05/16 aggiungere una finally
        Message4Debug.log("hai impiegato " + (System.currentTimeMillis() - a) + " millisecondi per leggere l'intero file");
        Message4Debug.log("\n\t\tDbFromRemoteServerTask.doInBackground().STOP = " + System.currentTimeMillis());

        return list;

    }
}
