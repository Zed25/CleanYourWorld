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

import com.ufos.cyw16.cleanyourworld.dal.ddl.CYWOpenHelper;
import com.ufos.cyw16.cleanyourworld.dal.ddl.ColumnType;
import com.ufos.cyw16.cleanyourworld.dal.ddl.Table;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;

public abstract class TableAdapter {
    private CYWOpenHelper openHelper;
    private String tableName;
    private Table table;
    private String csvSeparator = ",";
    private String webSite = "http://www.ubuntumby.altervista.org/cyw16.csv/cyw16_table_";
    private String extensionFile = ".csv";

    public TableAdapter(Context context, String tableName) {
        this.openHelper = CYWOpenHelper.getInstance(context);
        this.tableName = tableName;
        this.table = openHelper.getTableByName(tableName);
    }

    public long insert(String[] key, String[] value) throws TableAdapterException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        long lastId = db.insert(tableName, null, contentValuesCasted(key, value));
        db.close();
        if (lastId > 0)
            return lastId;
        String exceptionMessage = "Insertion fails for";
        for (int i = 0; i < key.length; i++)
            exceptionMessage += " (" + key[i] + " : " + value[i] + " )";
        throw new TableAdapterException(exceptionMessage);
    }

    public void insertBigData(ArrayList<String[]> rows) {
        new DbInsertBigDataTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Message4Debug.log("\n\tDbInsertBigDataTask.onPreExecute() = " + System.currentTimeMillis());
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Message4Debug.log("\n\tDbInsertBigDataTask.onPostExecute() = " + System.currentTimeMillis());

            }

            @Override
            protected void onProgressUpdate(String[]... values) {
                super.onProgressUpdate(values);
                try {
                    SQLiteDatabase db = openHelper.getWritableDatabase();
                    db.insert(tableName, null, contentValuesCasted(values[0], values[1]));
                    db.close();
                } catch (TableAdapterException e) {
                    Message4Debug.log(e.toString());
                }
            }
        }.execute(rows);
    }

    public int update(String[] key, String[] newValues, String[] whereClauses, String[] whereArgs) throws TableAdapterException {

        SQLiteDatabase db = openHelper.getWritableDatabase();
        String whereClause = whereClauseElaborate(whereClauses);
        if (whereClauses.length != whereArgs.length)
            throw new TableAdapterException("Numbers of elements of 'whereClauses[]' is different from that of 'whereArgs[]'");
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
        throw new TableAdapterException(tableName + ": " + exceptionMessage);
    }


    public int delete(String[] whereClauses, String[] whereArgs) throws TableAdapterException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        String whereClause = whereClauseElaborate(whereClauses);
        if (whereClauses.length != whereArgs.length)
            throw new TableAdapterException("Numbers of elements of 'whereClauses[]' is different from that of 'whereArgs[]'");
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
        throw new TableAdapterException(tableName + ": " + exceptionMessage);

    }

    public int deleteAllRows() throws TableAdapterException {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        int id = db.delete(tableName, null, null);
        db.close();
        if (id > 0) {
            return id;
        }
        throw new TableAdapterException(tableName + ": " + "Impossible to empty the table");

    }


    public void getData() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query(table.getName(), null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String[] columnNames = cursor.getColumnNames();
            String output = "";
            for (String columnName : columnNames) {
                output += columnName + ": " + cursor.getString(cursor.getColumnIndex(columnName)) + " ";
            }
            Message4Debug.log(output + "\n");
        }
    }

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
                } catch (TableAdapterException e) {
                    Message4Debug.log(e.toString());
                }
                Message4Debug.log("\n\tDbFromRemoteServerTask.onPreExecute() = " + System.currentTimeMillis());

            }

            @Override
            protected void onPostExecute(ArrayList<String[]> strings) {
                super.onPostExecute(strings);
                Message4Debug.log("\n\tDbFromRemoteServerTask().onPostExecute() = " + System.currentTimeMillis());
                insertBigData(strings); // TODO: 04/06/16 verificare questa riga
            }

            @Override
            protected void onProgressUpdate(String[]... values) {
                super.onProgressUpdate(values);

            }
        }.execute(params);
    }

    private ContentValues contentValuesCasted(String[] key, String[] values) throws TableAdapterException {
        if (key.length != values.length)
            throw new TableAdapterException("Numbers of elements of 'key[]' is different from that of 'values[]'");

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
                    throw new TableAdapterException("Cast of typeCode: " + columnType + ", for " + key[i] + " = " + values[i] + " is not supported");
            }
        }
        return contentValues;


    }

    private String whereClauseElaborate(String[] whereClauses) throws TableAdapterException {
        if (whereClauses == null)
            throw new TableAdapterException("The 'whereClauses[]' can not be 'null'");
        String str = tableName + "." + whereClauses[0] + " = ?";
        for (int i = 1; i < whereClauses.length; i++) {
            str += " AND " + tableName + "." + whereClauses[i] + " = ?";
        }
        return str;
    }

    public int getCount() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        return c.getCount();
    }
}
