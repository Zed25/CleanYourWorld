package com.ufos.cyw16.cleanyourworld.dal.ddl;

/**
 * The type Column.
 * This class allow the creation Column object.
 * its an aggregation of Table object
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
