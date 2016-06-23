/*
 * Created by Umberto Ferracci from urania and published on 23/06/16 17.49
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.Table
 * File name: Table.java
 * Class name: Table
 * Last modified: 23/06/16 17.34
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.Table
 * File name: Table.java
 * Class name: Table
 * Last modified: 09/06/16 16.43
 */

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

/**
 * The type Table.
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
