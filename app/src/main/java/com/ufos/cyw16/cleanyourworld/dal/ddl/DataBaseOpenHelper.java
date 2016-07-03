package com.ufos.cyw16.cleanyourworld.dal.ddl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The type Data base open helper.
 */
// class that helps to create a new database TEDxTV16.db if it doesn't exist, loads it if it's already created
// and lets you read and write on that database
@Deprecated
public class DataBaseOpenHelper extends SQLiteOpenHelper {

    // database version. helps to upgrade to new version
    private static final int DATABASE_VERSION = 1;
    // database name on the device
    private static final String DATABASE_NAME = "cyw16.db";

    // SQL create Regioni Table (if first time)
    private static final String REGIONI_TABLE_CREATE_STATEMENT = "CREATE TABLE `Regioni` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT NOT NULL,\n)";

    private static final String PROVINCE_TABLE_CREATE_STATEMENT = "CREATE TABLE `Province` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\t`id_regione`\tINTEGER NOT NULL,\n)";

    private static final String COMUNI_TABLE_CREATE_STATEMENT = "CREATE TABLE `Comuni` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\t`id_provincia`\tINTEGER NOT NULL,\n)";

    private static final String ISOLA_ECOLOGICA_TABLE_CREATE_STATEMENT = "CREATE TABLE `IsolaEcologica` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`id_comune`\tINTEGER NOT NULL,\n" +
            "\t`address`\tTEXT NOT NULL,\n" +
            "\t`description`\tTEXT,\n" +
            "\t`coordinates`\tTEXT NOT NULL\n" +
            ")";

    private static final String PRODUCT_TYPE_TABLE_CREATE_STATEMENT = "CREATE TABLE `ProductType` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`id_materials`\tINTEGER NOT NULL,\n" +
            "\t`name`\tTEXT NOT NULL,\n)";

    private static final String COLLECTION_TYPE_TABLE_CREATE_STATEMENT = "CREATE TABLE `CollectionType` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT NOT NULL,\n)";

    private static final String PRODUCTS_TABLE_CREATE_STATEMENT = "CREATE TABLE `Products` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\t`EAN`\tTEXT NOT NULL,\n" +
            "\t`barcode`\tTEXT NOT NULL,\n" +
            "\t`description`\tTEXT,\n" +
            "\t`productType_id`\tINTEGER NOT NULL\n" +
            ")";

    private static final String MATERIALS_TABLE_CREATE_STATEMENT = "CREATE TABLE `Materials` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT NOT NULL,\n)";

    private static final String COLORS_TABLE_CREATE_STATEMENT = "CREATE TABLE `Colors` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\t`colorCode`\tTEXT NOT NULL,\n)";

    private static final String COLLECTION_TABLE_CREATE_STATEMENT = "CREATE TABLE `Collection` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`id_comune`\tINTEGER NOT NULL,\n" +
            "\t`id_materiale`\tINTEGER NOT NULL,\n" +
            "\t`id_day`\tINTEGER NOT NULL,\n" +
            "\t`id_color`\tINTEGER NOT NULL,\n" +
            "\t`id_collectionType`\tINTEGER NOT NULL,\n)";

    /**
     * Instantiates a new Data base open helper.
     *
     * @param context the context
     */
// constructor. it needs the context of the activity to create
    public DataBaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // called AUTOMATICALLY  when the class doesn't find the DB on the device and creates the tables
    // don't call
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(REGIONI_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(PROVINCE_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(COMUNI_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(ISOLA_ECOLOGICA_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(PRODUCT_TYPE_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(COLLECTION_TYPE_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(PRODUCTS_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(MATERIALS_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(COLORS_TABLE_CREATE_STATEMENT);
        sqLiteDatabase.execSQL(COLLECTION_TABLE_CREATE_STATEMENT);
    }

    // needed for future upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
