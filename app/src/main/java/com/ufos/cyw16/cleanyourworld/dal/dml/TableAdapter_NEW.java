/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter_NEW
 * Last modified: 04/07/16 11.18
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter_NEW
 * Last modified: 03/07/16 18.27
 */

package com.ufos.cyw16.cleanyourworld.dal.dml;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
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
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The type Table adapter.
 * This class allow you to communicate with the database through the CYWOpenHelper.
 * It implements standard methods of the CRUD functions and methods for connection to the server
 */
public class TableAdapter_NEW {
    private CYWOpenHelper openHelper;
    private String tableName;
    private Table table;
    private String url = "http://www.ubuntumby.altervista.org/cyw16/?androidApp=true";
    private String csvSeparator = ",";

    /**
     * Instantiates a new TableAdapter.
     *
     * @param context   the context
     * @param tableName the table name
     */
    public TableAdapter_NEW(Context context, String tableName) {
        this.openHelper = CYWOpenHelper.getInstance(context);
        this.tableName = tableName;
        this.table = openHelper.getTableByName(tableName);
    }

    /**
     * Gets the number of rows in the table
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
     * Inserts data into the table of the database
     *
     * @param key   the key
     * @param value the value
     * @return the long
     * @throws DaoException the dao exception
     */
    public long insert(String[] key, String[] value) throws InsertDaoException, DaoException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        long lastId = -10;
        try {
            lastId = db.insertOrThrow(tableName, null, contentValuesCasted(key, value));
        } catch (SQLiteConstraintException e) {
            db.endTransaction();
            db.close();
            throw new InsertDaoException("Element already present in the database: " + e.getMessage());
        } catch (SQLException e) {
            db.endTransaction();
            db.close();
            throw new DaoException("SQLException: " + e.getMessage());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        if (lastId > 0)
            return lastId;
        String exceptionMessage = "Insertion fails for";
        for (int i = 0; i < key.length; i++)
            exceptionMessage += " (" + key[i] + " : " + value[i] + " )";
        throw new DaoException(exceptionMessage);
    }


    /**
     * Updates the row in the table of the database
     *
     * @param key          the key
     * @param newValues    the new values
     * @param whereClauses the where clauses
     * @param whereArgs    the where args
     * @return the int
     * @throws DaoException the dao exception
     */
    public int update(String[] key, String[] newValues, String[] whereClauses, String[] whereArgs) throws DaoException, UpdateDaoException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        String whereClause = whereClauseElaborate(whereClauses);
        if (whereClauses.length != whereArgs.length)
            throw new DaoException("Numbers of elements of 'whereClauses[]' is different from that of 'whereArgs[]'");
        db.beginTransaction();
        int id = db.update(tableName, contentValuesCasted(key, newValues), whereClause, whereArgs);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        if (id > 0)
            return id;
        String exceptionMessage = "Updated failed for";
        exceptionMessage += " '" + whereClause + " = ";
        exceptionMessage += "(" + whereArgs[0];
        for (int i = 1; i < whereArgs.length; i++)
            exceptionMessage += ", " + whereArgs[i];
        exceptionMessage += ")';";
        throw new UpdateDaoException(tableName + ": " + exceptionMessage);
    }


    /**
     * Deletes the row from the table of the database
     *
     * @param whereClauses the where clauses
     * @param whereArgs    the where args
     * @return the int
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
     * Delete all rows from the table of the database
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    private int deleteAllRows() throws DaoException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
//        if (getCount() > 0) {
        int id = db.delete(tableName, null, null);
        db.close();
        return id;
//        }
//        throw new DaoException("");

    }


    /**
     * Gets the rows from the table
     *
     * @param selectionClauses the selection clauses
     * @param selectionArgs    the selection args
     * @param orderBy          the order by
     * @return the data
     * @throws DaoException the dao exception
     */
    public List<String[]> getData(String[] selectionClauses, String[] selectionArgs, String orderBy) throws DaoException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        String whereclasues = whereClauseElaborate(selectionClauses, true);
        Cursor cursor = db.query(table.getName(), null, whereclasues, selectionArgs, null, null, orderBy);
        List<String[]> rows = new ArrayList<>();
        while (cursor.moveToNext()) {
            int columns = cursor.getColumnNames().length;
            String[] row = new String[columns];
            for (int i = 0; i < columns; i++) {
                row[i] = cursor.getString(i);
            }
            rows.add(row);
        }
        cursor.close();
        if (rows.size() < 1) {
            String ss = "";
            if (selectionArgs != null) {
            for (String s : selectionArgs) {
                ss += s + " ";
            }
            }
            throw new DaoException("There aren't elements in table " + tableName + " where <" + whereclasues + "> " + ss);
        }
        return rows;
    }


    /**
     * Updates the table of the internal database from the remote database
     * This method is multi thread, it connects with the server
     * and simultaneously enters the data in the table
     *
     * @param keys   the keys
     * @param values the values
     * @throws DaoException         the dao exception
     * @throws InterruptedException the interrupted exception
     */
    public void updateFromServer(String[] keys, final String[] values) throws DaoException, InterruptedException {
        deleteAllRows();
        String query = url + "&operation=secureSelect&table=" + tableName;
        if (keys != null)
            for (int i = 0; i < keys.length; i++) {
                query += "&" + keys[i] + "=" + values[i];
            }
        final String finalQuery = query;
        Message4Debug.log(query);

        /* queue shared between the thread of the server reading and the insertion */
        BlockingQueue<String[][]> queue = new ArrayBlockingQueue(11);

        /* Thread of the insertion */
        class RunnableDb implements Runnable {
            private final BlockingQueue<String[][]> queue;
            private SQLiteDatabase db;

            RunnableDb(BlockingQueue<String[][]> queue) {
                this.queue = queue;
                this.db = openHelper.getWritableDatabase();
            }

            @Override
            public void run() {
                db.beginTransaction();
                try {
                    String[][] take = queue.take();
                    while (take[0] != null & take[1] != null) {
                        String[] keys = take[0];
                        String[] values = take[1];
                        take = queue.take();
                        db.insert(tableName, null, contentValuesCasted(keys, values));
                    }
                } catch (InterruptedException e) {
                    Message4Debug.log(e.getMessage());
                } catch (DaoException e) {
                    Message4Debug.log(e.getMessage());
                } finally {
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                }
            }
        }

        /* Thread of the server reading */
        class RunnableServer implements Runnable {
            private final BlockingQueue<String[][]> queue;

            RunnableServer(BlockingQueue queue) {
                this.queue = queue;
            }

            @Override
            public void run() {
                try {
                    URL url = new URL(finalQuery);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line;
                    String[] keys = String.valueOf(Html.fromHtml(br.readLine())).split(csvSeparator); /* name of fields */
                    while ((line = br.readLine()) != null) {
                        String[] values = String.valueOf(Html.fromHtml(line)).split(csvSeparator);
                        queue.put(new String[][]{keys, values});
                    }
                    queue.put(new String[][]{null, null});
                } catch (IOException e) {
                    Message4Debug.log(e.getMessage());
                } catch (InterruptedException e) {
                    Message4Debug.log(e.getMessage());
                }
            }
        }
        /* Run of the thread */
        Thread threadServer = new Thread(new RunnableServer(queue));
        Thread threadDb = new Thread(new RunnableDb(queue));
        long start = System.currentTimeMillis();
        threadServer.start();
        threadDb.start();
        threadServer.join();
        threadDb.join();
        Message4Debug.log("Total time: " + (System.currentTimeMillis() - start) + " ms");

    }

    /**
     * ContentValuesCasted.
     * This method creates the ContentValues by two arrays of string.
     * It casts the string taking of the table column type
     *
     * @param key    the key
     * @param values the values
     * @return the content values
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
                    throw new DaoException("Castig of typeCode: " + columnType + ", for " + key[i] + " = " + values[i] + " is not supported");
            }
        }
        return contentValues;


    }

    /**
     * Where clause elaborate string.
     * This method creates the where clasuse by two arrays of string.
     *
     * @param whereClauses the where clauses
     * @param isNullable   the is nullable
     * @return the string
     * @throws DaoException the dao exception
     */
    private String whereClauseElaborate(String[] whereClauses, boolean isNullable) throws DaoException {
        if (whereClauses == null & !isNullable)
            throw new DaoException("The 'whereClauses[]' can not be 'null'");
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
     * This method creates the where clasuse by two arrays of string.
     *
     * @param whereClauses the where clauses
     * @return the string
     * @throws DaoException the dao exception
     */
    private String whereClauseElaborate(String[] whereClauses) throws DaoException {
        return whereClauseElaborate(whereClauses, false);
    }

    /**
     * Send to server.
     *
     * @throws DaoException the dao exception
     */
    public void sendToServer(String[] keys, String values[]) throws DaoException, InterruptedException {
        String query = url + "&operation=secureInsert&table=" + tableName;
        for (int i = 0; i < keys.length; i++) {
            query += "&" + keys[i] + "=" + values[i];
        }
        Message4Debug.log(query);
        final String finalQuery = query;

        class RunnableServer implements Runnable {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(finalQuery);
                    Message4Debug.log("sdera "+finalQuery);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line = br.readLine();
                    //connection.disconnect();
                } catch (IOException e) {
                    Message4Debug.log(e.getMessage());
                } finally {
                   // if (connection != null)
                     //   connection.disconnect();
                }
            }
        }
        Thread threadServer = new Thread(new RunnableServer());
        threadServer.start();
        threadServer.join();
    }

    /**
     * Gets table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets table name.
     *
     * @param tableName the table name
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

