/*
 * Created by Umberto Ferracci from urania on 04/06/16 18.06
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.Column
 * File name: Column.java
 * Class name: Column
 * Last modified: 03/06/16 19.41
 */

/*
 * Created by Umberto Ferracci from urania on 02/06/16 13.04
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.Column
 * File name: Column.java
 * Class name: Column
 * Last modified: 01/06/16 22.46
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
