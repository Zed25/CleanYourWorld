package com.ufos.cyw16.cleanyourworld.dal.ddl;


import java.util.HashMap;

/**
 * The type Table.
 * This class allows the creation object Table
 * useful for the creation of the database
 */
public class Table {

    private String name;
    private Column[] columns;
    private HashMap<String, Column> columnHashMap;

    /**
     * Instantiates a new Table.
     *
     * @param name    the name
     * @param columns the columns
     */
    public Table(String name, Column[] columns) {
        this.name = name;
        this.columns = columns;
        columnHashMap = new HashMap<>();
        for (Column c : columns) {
            columnHashMap.put(c.getColumnName(), c);
        }
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get columns column [ ].
     *
     * @return the column [ ]
     */
    public Column[] getColumns() {
        return columns;
    }

    /**
     * Gets column type.
     *
     * @param columnName the column name
     * @return the column type
     */
    public int getColumnType(String columnName) {
        return columnHashMap.get(columnName).getColumnTypeCode();
    }

}
