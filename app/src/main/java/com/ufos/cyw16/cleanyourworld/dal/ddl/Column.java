/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.Column
 * File name: Column.java
 * Class name: Column
 * Last modified: 09/06/16 16.39
 */

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

/**
 * The type Column.
 */
public class Column {

    private String columnName;
    private String columnType;
    private int columnTypeCode;
    private String extraConditions;

    /**
     * Instantiates a new Column.
     *
     * @param fieldName       the field name
     * @param fieldType       the field type
     * @param extraConditions the extra conditions
     */
    public Column(String fieldName, String fieldType, String extraConditions) {
        this.columnName = fieldName;
        this.columnType = fieldType;
        this.extraConditions = extraConditions;
        this.columnTypeCode = ColumnType.getTypeCode(fieldType);
    }

    /**
     * Instantiates a new Column.
     *
     * @param name the name
     * @param type the type
     */
    public Column(String name, String type) {
        this(name, type, "");
    }

    /**
     * Gets column name.
     *
     * @return the column name
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Gets column type code.
     *
     * @return the column type code
     */
    public int getColumnTypeCode() {
        return columnTypeCode;
    }

    /**
     * Gets column type.
     *
     * @return the column type
     */
    public String getColumnType() {
        return columnType;
    }

    /**
     * Gets extra conditions.
     *
     * @return the extra conditions
     */
    public String getExtraConditions() {
        return extraConditions;
    }

}
