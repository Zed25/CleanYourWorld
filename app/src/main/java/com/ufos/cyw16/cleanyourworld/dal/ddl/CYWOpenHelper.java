package com.ufos.cyw16.cleanyourworld.dal.ddl;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type CYWOpenHelper.
 * This class creates, updates and deletes database tables
 * implements SQLiteOpenHelper
 * This class is a Singleton class.
 */
public class CYWOpenHelper extends SQLiteOpenHelper {
    private final static int db_version = 3;
    private final static String db_name = "cyw16.db";
    private static CYWOpenHelper mInstance = null;
    private ArrayList<Table> tables;
    private HashMap<String, Table> tableHashMap;


    /**
     * Instantiates a new CYWOpenHelper.
     * Creates the objects Table
     * and generates tablesHasMap for each Table
     *
     * @param context the context
     */
    public CYWOpenHelper(Context context) {
        super(context, db_name, null, db_version);
        tables = new ArrayList<Table>();
        tableHashMap = new HashMap<String, Table>();

        /* creates all database tables an adds all */
        tables.add(new Table("regioni", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("regione", "TEXT")
        }));
        tables.add(new Table("province", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("provincia", "TEXT"),
                new Column("regioni_id", "INTEGER")
        }));
        tables.add(new Table("comuni", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("comune", "TEXT"),
                new Column("province_id", "INTEGER")
        }));
        tables.add(new Table("isolaEcologica", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("comuni_id", "INTEGER"),
                new Column("indirizzo", "TEXT"),
                new Column("coordinate", "TEXT"),
                new Column("descrizione", "TEXT")
        }));
        tables.add(new Table("preferiti", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("comuni_id", "INTEGER")
        }));
        tables.add(new Table("tipologiaProdotti", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("nome", "TEXT"),
                new Column("materiali_id", "INTEGER")
        }));
        tables.add(new Table("tipologiaRaccolta", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("nome", "TEXT")
        }));
        tables.add(new Table("prodotti", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("nome", "TEXT"),
                new Column("descrizione", "TEXT"),
                new Column("EAN", "TEXT"),
                new Column("tipologiaProdotti_id", "INTEGER")
        }));
        tables.add(new Table("materiali", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("nome", "TEXT")
        }));
        tables.add(new Table("giorni", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("nome", "TEXT")
        }));
        tables.add(new Table("colori", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("colore", "TEXT"),
                new Column("codiceColore", "TEXT")
        }));
        tables.add(new Table("raccolta", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY"),
                new Column("comuni_id", "INTEGER"),
                new Column("materiali_id", "INTEGER"),
                new Column("giorni_id", "INTEGER"),
                new Column("colori_id", "INTEGER"),
                new Column("tipologiaRaccolta_id", "INTEGER"),
        }));
        tables.add(new Table("productScan", new Column[]{
                new Column("_id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"),
                new Column("prodotti_id", "INTEGER", "UNIQUE"),
                new Column("date", "TEXT"),

        }));

        /* HashMap of tables */
        for (Table t : tables) {
            tableHashMap.put(t.getName(), t);
        }
    }

    /**
     * Gets instance.
     * CYWOpenHelper is a Singleton class
     *
     * @param context the context
     * @return the instance
     */
    public static synchronized CYWOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CYWOpenHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            for (Table t : tables) {
                createTable(db, t);
            }
        } catch (SQLException e) {
            Message4Debug.log(e.getMessage() + "\n" + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            for (Table t : tables) {
                dropTable(db, t);
                createTable(db, t);
            }
        } catch (SQLException e) {
            Message4Debug.log(e.getMessage() + "\n" + e.toString());
        }
    }

    /**
     * Drop table.
     * This method drop the table from database.
     *
     * @param db    the db
     * @param table the table
     * @throws SQLException the sql exception
     */
    private void dropTable(SQLiteDatabase db, Table table) throws SQLException {
        String qry = "DROP TABLE IF EXISTS " + table.getName() + ";";
        Message4Debug.log(qry);
        db.execSQL(qry);

    }

    /**
     * Create table.
     * This method creates all tables of the database
     * it takes all columns of the table and generates SQL query, in example:
     * "CREATE TABLE 'tableName' (column1 typeColumn1 extraConditionColumn1, column2 typeColumn2 extraconditionColumn2, ...)"
     *
     * @param db    the db
     * @param table the table
     * @throws SQLException the sql exception
     */
    private void createTable(SQLiteDatabase db, Table table) throws SQLException {
        String qry = "CREATE TABLE " + table.getName() + " (";
        for (Column column : table.getColumns()) {
            qry += " " + column.getColumnName() +
                    " " + column.getColumnType() +
                    " " + column.getExtraConditions() +
                    ",";
        }
        qry = qry.substring(0, qry.length() - 1) + ");";
        Message4Debug.log(qry);
        db.execSQL(qry);

    }

    /**
     * Alter table.
     */
    private void alterTable() {
        // TODO: 02/06/16
    }

    /**
     * Gets table by name.
     *
     * @param name the name
     * @return the table by name
     */
    public Table getTableByName(String name) {
        return tableHashMap.get(name);
    }
}
