
/*
 * Created by Umberto Ferracci from urania and published on 17/06/16 7.44
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.DbQueryServerTask
 * File name: DbQueryServerTask.java
 * Class name: DbQueryServerTask
 * Last modified: 17/06/16 7.41
 */

package com.ufos.cyw16.cleanyourworld.dal.dml;

import android.os.AsyncTask;
import android.text.Html;

import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type Db query server task.
 * @deprecated
 */
public class DbQueryServerTask extends AsyncTask<String, Void, ArrayList<String[]>> {
    @Override
    protected ArrayList<String[]> doInBackground(String... params) {
        try {
            Message4Debug.log(params[0]);
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            ArrayList<String[]> list = new ArrayList<>();
//            String[] key= br.readLine().split(params[1]); // TODO: provare con inserimento singolo
            while ((line = br.readLine()) != null) {
                Message4Debug.log("asdq\t\t\t\t:" + line);
                String[] row = line.split(params[1]);
                for (int i = 0; i < row.length; i++) {
                    row[i] = String.valueOf(Html.fromHtml(row[i]));
                }
                list.add(row);
            }
            return list;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
