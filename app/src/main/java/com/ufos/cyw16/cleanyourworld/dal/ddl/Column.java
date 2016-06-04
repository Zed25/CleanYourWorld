/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.Column
 * File name: Column.java
 * Class name: Column
 * Last modified: 04/06/16 20.15
 */

package com.ufos.cyw16.cleanyourworld.dal.ddl;

public class Column {

    private String columnName;
    private String columnType;
    private int columnTypeCode;
    private String extraConditions;

    public Column(String fieldName, String fieldType, String extraConditions) {
        this.columnName = fieldName;
        this.columnType = fieldType;
        this.extraConditions = extraConditions;
        this.columnTypeCode = ColumnType.getTypeCode(fieldType);
    }

    public Column(String name, String type) {
        this(name, type, "");
    }

    public String getColumnName() {
        return columnName;
    }

    public int getColumnTypeCode() {
        return columnTypeCode;
    }

    public String getColumnType() {
        return columnType;
    }

    public String getExtraConditions() {
        return extraConditions;
    }

}
