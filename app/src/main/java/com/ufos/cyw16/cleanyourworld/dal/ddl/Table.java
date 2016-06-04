/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.Table
 * File name: Table.java
 * Class name: Table
 * Last modified: 04/06/16 20.15
 */

package com.ufos.cyw16.cleanyourworld.dal.ddl;


import java.util.HashMap;

public class Table {

    private String name;
    private Column[] columns;
    private HashMap<String, Column> columnHashMap;

    public Table(String name, Column[] columns) {
        this.name = name;
        this.columns = columns;
        columnHashMap = new HashMap<>();
        for (Column c : columns) {
            columnHashMap.put(c.getColumnName(), c);
        }
    }

    public String getName() {
        return name;
    }

    public Column[] getColumns() {
        return columns;
    }

    public int getColumnType(String columnName) {
        return columnHashMap.get(columnName).getColumnTypeCode();
    }

}
