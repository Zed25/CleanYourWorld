/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.CYWOpenHelper
 * File name: CYWOpenHelper.java
 * Class name: CYWOpenHelper
 * Last modified: 09/06/16 17.36
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 15.40
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.CYWOpenHelper
 * File name: CYWOpenHelper.java
 * Class name: CYWOpenHelper
 * Last modified: 09/06/16 14.32
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 12.16
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.CYWOpenHelper
 * File name: CYWOpenHelper.java
 * Class name: CYWOpenHelper
 * Last modified: 09/06/16 11.42
 */

/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.CYWOpenHelper
 * File name: CYWOpenHelper.java
 * Class name: CYWOpenHelper
 * Last modified: 04/06/16 20.15
 */

package com.ufos.cyw16.cleanyourworld.dal.ddl;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Cyw open helper.
 */
public class CYWOpenHelper extends SQLiteOpenHelper {
    private final static int db_version = 1;
    private final static String db_name = "cyw16.db";
    private static CYWOpenHelper mInstance = null;
    private ArrayList<Table> tables;
    private HashMap<String, Table> tableHashMap;


    /**
     * Instantiates a new Cyw open helper.
     *
     * @param context the context
     */
    private CYWOpenHelper(Context context) {
        super(context, db_name, null, db_version);
        tables = new ArrayList<Table>();
        tableHashMap = new HashMap<String, Table>();

        /* inserimento tabello in tables */
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


        /* HashMap delle tabelle */
        for (Table t : tables) {
            tableHashMap.put(t.getName(), t);
        }
    }

    /**
     * Gets instance.
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
            Message4Debug.log(e.toString() + "\n" + e.getMessage());
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
            Message4Debug.log(e.toString() + "\n" + e.getMessage());
        }
    }

    /**
     * Create table.
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
     * Drop table.
     *
     * @param db    the db
     * @param table the table
     * @throws SQLException the sql exception
     */
    private void dropTable(SQLiteDatabase db, Table table) throws SQLException {
        String qry = "DROP TABLE IF EXISTS" + table.getName() + ";";
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
