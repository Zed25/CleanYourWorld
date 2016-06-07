/*
 * Created by Umberto Ferracci from urania and published on 07/06/16 5.17
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter
 * File name: TableAdapter.java
 * Class name: TableAdapter
 * Last modified: 07/06/16 5.13
 */

/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter
 * File name: TableAdapter.java
 * Class name: TableAdapter
 * Last modified: 04/06/16 20.11
 */

package com.ufos.cyw16.cleanyourworld.dal.dml;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;

import com.ufos.cyw16.cleanyourworld.dal.ddl.CYWOpenHelper;
import com.ufos.cyw16.cleanyourworld.dal.ddl.ColumnType;
import com.ufos.cyw16.cleanyourworld.dal.ddl.Table;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type Table adapter.
 */
public abstract class TableAdapter {
    private CYWOpenHelper openHelper;
    private String tableName;
    private Table table;
    private String webSite = "http://www.ubuntumby.altervista.org/cyw16.csv/cyw16_table_";
    private String url = "http://www.ubuntumby.altervista.org/cyw16/?androidApp=true";
    private String csvSeparator = ",";
    private String extensionFile = ".csv";

    /**
     * Instantiates a new Table adapter.
     *
     * @param context   the context
     * @param tableName the table name
     */
    public TableAdapter(Context context, String tableName) {
        this.openHelper = CYWOpenHelper.getInstance(context);
        this.tableName = tableName;
        this.table = openHelper.getTableByName(tableName);
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        int id = c.getCount();
        db.close();
        return id;
    }

    /**
     * Insert long.
     *
     * @param key   the key
     * @param value the value
     *
     * @return the long
     *
     * @throws DaoException the dao exception
     */
    public long insert(String[] key, String[] value) throws DaoException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        long lastId = db.insert(tableName, null, contentValuesCasted(key, value));
        db.close();
        if (lastId > 0)
            return lastId;
        String exceptionMessage = "Insertion fails for";
        for (int i = 0; i < key.length; i++)
            exceptionMessage += " (" + key[i] + " : " + value[i] + " )";
        throw new DaoException(exceptionMessage);
    }

    /**
     * Insert big data.
     *
     * @param rows the rows
     *
     * @throws DaoException the dao exception
     * @deprecated
     */
    public void insertBigData(final ArrayList<String[]> rows) throws DaoException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String[] key = rows.get(0);
                for (int i = 1; i < rows.size(); i++) {
                    try {
                        insert(key, rows.get(i));
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        long start = System.currentTimeMillis();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Message4Debug.log(e.toString());
        }
        Message4Debug.log("Esecuzione del thread in: " + (start - System.currentTimeMillis()) + " ms");


//        new DbInsertBigDataTask() {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                Message4Debug.log("\n\tDbInsertBigDataTask.onPreExecute() = " + System.currentTimeMillis());
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                Message4Debug.log("\n\tDbInsertBigDataTask.onPostExecute() = " + System.currentTimeMillis());
//
//            }
//
//            @Override
//            protected void onProgressUpdate(String[]... values) {
//                super.onProgressUpdate(values);
//                try {
//                    SQLiteDatabase db = openHelper.getWritableDatabase();
//                    db.insert(tableName, null, contentValuesCasted(values[0], values[1]));
//                    db.close();
//                } catch (DaoException e) {
//                    Message4Debug.log(e.toString());
//                }
//            }
//        }.execute(rows);
    }

    /**
     * Update int.
     *
     * @param key          the key
     * @param newValues    the new values
     * @param whereClauses the where clauses
     * @param whereArgs    the where args
     *
     * @return the int
     *
     * @throws DaoException the dao exception
     */
    public int update(String[] key, String[] newValues, String[] whereClauses, String[] whereArgs) throws DaoException {

        SQLiteDatabase db = openHelper.getWritableDatabase();
        String whereClause = whereClauseElaborate(whereClauses);
        if (whereClauses.length != whereArgs.length)
            throw new DaoException("Numbers of elements of 'whereClauses[]' is different from that of 'whereArgs[]'");
        int id = db.update(tableName, contentValuesCasted(key, newValues), whereClause, whereArgs);
        db.close();
        if (id > 0)
            return id;
        String exceptionMessage = "Updated failed for";
        exceptionMessage += " '" + whereClause + " = ";
        exceptionMessage += "(" + whereArgs[0];
        for (int i = 1; i < whereArgs.length; i++)
            exceptionMessage += ", " + whereArgs[i];
        exceptionMessage += ")';";
        throw new DaoException(tableName + ": " + exceptionMessage);
    }


    /**
     * Delete int.
     *
     * @param whereClauses the where clauses
     * @param whereArgs    the where args
     *
     * @return the int
     *
     * @throws DaoException the dao exception
     */
    public int delete(String[] whereClauses, String[] whereArgs) throws DaoException {
        if (whereClauses.length != whereArgs.length)
            throw new DaoException("Numbers of elements of 'whereClauses[]' is different from that of 'whereArgs[]'");
        SQLiteDatabase db = openHelper.getWritableDatabase();
        String whereClause = whereClauseElaborate(whereClauses);
        int id = db.delete(tableName, whereClause, whereArgs);
        db.close();
        if (id > 0)
            return id;
        String exceptionMessage = "Cancellation failed for";
        exceptionMessage += " '" + whereClause + " = ";
        exceptionMessage += "(" + whereArgs[0];
        for (int i = 1; i < whereArgs.length; i++)
            exceptionMessage += ", " + whereArgs[i];
        exceptionMessage += ")';";
        throw new DaoException(tableName + ": " + exceptionMessage);

    }

    /**
     * Delete all rows int.
     *
     * @return the int
     *
     * @throws DaoException the dao exception
     */
    public int deleteAllRows() throws DaoException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        int id = db.delete(tableName, null, null);
        db.close();
        if (id > 0) {
            return id;
        }
        throw new DaoException(tableName + ": " + "Impossible to empty the table");
    }


    /**
     * Gets data.
     *
     * @param selectionClauses the selection clauses
     * @param selectionArgs    the selection args
     *
     * @return the data
     *
     * @throws DaoException the dao exception
     */
    public ArrayList<ArrayList<String>> getData(String[] selectionClauses, String[] selectionArgs) throws DaoException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query(table.getName(), null, whereClauseElaborate(selectionClauses, true), selectionArgs, null, null, null);
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] columnNames = cursor.getColumnNames();
            String output = "";
            ArrayList<String> row = new ArrayList<>();
            for (String columnName : columnNames) {
                row.add(cursor.getString(cursor.getColumnIndex(columnName)));
                output += columnName + ": " + cursor.getString(cursor.getColumnIndex(columnName)) + " ";
            }
            result.add(row);
            Message4Debug.log(output + "\n");
        }
        return result;
    }

    /**
     * Update from remote server.
     *
     * @deprecated
     */
    public void updateFromRemoteServer() {
        ArrayList<String[]> result = new ArrayList<>();
        String query = webSite + tableName + extensionFile;
        String[] params = {query, csvSeparator};
        final ArrayList<String[]>[] rows = new ArrayList[1];
        new DbFromRemoteServerTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                try {
                    deleteAllRows();
                } catch (DaoException e) {
                    Message4Debug.log(e.toString());
                }

            }

            @Override
            protected void onPostExecute(ArrayList<String[]> strings) {
                super.onPostExecute(strings);
                try {
                    insertBigData(strings); // TODO: 04/06/16 verificare questa riga
                } catch (DaoException e) {
                    Message4Debug.log(e.toString());
                }

            }

            @Override
            protected void onProgressUpdate(String[]... values) {
                super.onProgressUpdate(values);
//                try {
//                    insert(values[0],values[1]);
//                } catch (TableAdapterException e) {
//                    e.printStackTrace();
//                }

            }
        }.execute(params);
    }


    /**
     * Query server.
     *
     * @param keys   the keys
     * @param values the values
     *
     * @throws DaoException the dao exception
     * @deprecated
     */
    public void queryServer(String[] keys, String[] values) throws DaoException {
        ArrayList<String[]> result = new ArrayList<>();
        String query = url + "&table=" + tableName;
        if (keys != null)
            for (int i = 0; i < keys.length; i++) {
                query += "&" + keys[i] + "=" + values[i];
            }
        new DbQueryServerTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                try {
                    deleteAllRows();
                } catch (DaoException e) {
                    Message4Debug.log(e.toString());
                }
            }

            @Override
            protected void onPostExecute(ArrayList<String[]> strings) {
                super.onPostExecute(strings);
                try {
                    insertBigData(strings);
                } catch (DaoException e) {
                    Message4Debug.log(e.toString());
                }
            }
        }.execute(new String[]{query, csvSeparator});
    }


    /**
     * Query server 2.
     *
     * @param keys   the keys
     * @param values the values
     *
     * @throws DaoException         the dao exception
     * @throws InterruptedException the interrupted exception
     */
    public void queryServer2(String[] keys, String[] values) throws DaoException, InterruptedException {
        ArrayList<String[]> result = new ArrayList<>();
        String query = url + "&table=" + tableName;
        if (keys != null)
            for (int i = 0; i < keys.length; i++) {
                query += "&" + keys[i] + "=" + values[i];
            }
        final String finalQuery = query;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    deleteAllRows(); // TODO: 07/06/16 da cancellare
                    URL url = new URL(finalQuery);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line;
                    String[] keys = String.valueOf(Html.fromHtml(br.readLine())).split(csvSeparator); /* name of fields */
                    while ((line = br.readLine()) != null) {
                        String[] values = String.valueOf(Html.fromHtml(line)).split(csvSeparator);
                        insert(keys, values);
                    }
                } catch (IOException e) {
                    Message4Debug.log(e.toString());
                } catch (DaoException e) {
                    Message4Debug.log(e.toString());
                }
            }
        };
        Thread thread = new Thread(runnable);
        long start = System.currentTimeMillis();
        thread.start();
        thread.join();
        Message4Debug.log("Total time: " + (System.currentTimeMillis() - start) + " ms");

    }

    /**
     * Content values casted content values.
     *
     * @param key    the key
     * @param values the values
     *
     * @return the content values
     *
     * @throws DaoException the dao exception
     */
    private ContentValues contentValuesCasted(String[] key, String[] values) throws DaoException {
        if (key.length != values.length)
            throw new DaoException("Numbers of elements of 'key[]' is different from that of 'values[]'");

        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < key.length; i++) {
            int columnType = table.getColumnType(key[i]);
            switch (columnType) {
                case ColumnType.FIELD_TYPE_INTEGER:
                    contentValues.put(key[i], Integer.parseInt(values[i]));
                    break;
                case ColumnType.FIELD_TYPE_FLOAT:
                    contentValues.put(key[i], Float.parseFloat(values[i]));
                    break;
                case ColumnType.FIELD_TYPE_STRING:
                    contentValues.put(key[i], values[i]);
                    break;
                default:
                    throw new DaoException("Cast of typeCode: " + columnType + ", for " + key[i] + " = " + values[i] + " is not supported");
            }
        }
        return contentValues;


    }


    /**
     * Where clause elaborate string.
     *
     * @param whereClauses the where clauses
     * @param isNullable   the is nullable
     *
     * @return the string
     *
     * @throws DaoException the dao exception
     */
    private String whereClauseElaborate(String[] whereClauses, boolean isNullable) throws DaoException {
        if (whereClauses == null & !isNullable)
            throw new DaoException("The 'whereClauses[]' can not be 'null'"); // TODO: 06/06/16 TOGLIERE QUESTA ECCEZIONE
        if (whereClauses == null)
            return null;
        String arg = "?";
        String str = tableName + "." + whereClauses[0] + " = " + arg;
        for (int i = 1; i < whereClauses.length; i++) {
            str += " AND " + tableName + "." + whereClauses[i] + " = " + arg;
        }
        return str;
    }

    /**
     * Where clause elaborate string.
     *
     * @param whereClauses the where clauses
     *
     * @return the string
     *
     * @throws DaoException the dao exception
     */
    private String whereClauseElaborate(String[] whereClauses) throws DaoException {
        return whereClauseElaborate(whereClauses, false);
    }
}

