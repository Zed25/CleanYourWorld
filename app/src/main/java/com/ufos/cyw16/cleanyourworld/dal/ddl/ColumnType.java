/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.ColumnType
 * File name: ColumnType.java
 * Class name: ColumnType
 * Last modified: 09/06/16 18.11
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 12.16
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.ColumnType
 * File name: ColumnType.java
 * Class name: ColumnType
 * Last modified: 09/06/16 11.39
 */

/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.ColumnType
 * File name: ColumnType.java
 * Class name: ColumnType
 * Last modified: 04/06/16 20.15
 */

package com.ufos.cyw16.cleanyourworld.dal.ddl;

import java.util.HashMap;

/**
 * The type Column type.
 */
public class ColumnType {
    public static final int FIELD_TYPE_NULL = 0;
    public static final int FIELD_TYPE_INTEGER = 1;
    public static final int FIELD_TYPE_FLOAT = 2;
    public static final int FIELD_TYPE_STRING = 3;
    public static final int FIELD_TYPE_BLOB = 4;
    private static HashMap<String, Integer> hashMaps;

    /**
     * Gets type code.
     *
     * @param fieldType the field type
     * @return the type code
     */
    public static int getTypeCode(String fieldType) {
        hashMapCalulate();
        return hashMaps.get(fieldType).intValue();
    }

    /**
     * Hash map calulate.
     */
    private static void hashMapCalulate() {
        hashMaps = new HashMap();
        hashMaps.put("NULL", ColumnType.FIELD_TYPE_NULL);
        hashMaps.put("INTEGER", ColumnType.FIELD_TYPE_INTEGER);
        hashMaps.put("FLOAT", ColumnType.FIELD_TYPE_FLOAT);
        hashMaps.put("STRING", ColumnType.FIELD_TYPE_STRING);
        hashMaps.put("TEXT", ColumnType.FIELD_TYPE_STRING);
        hashMaps.put("BLOB", ColumnType.FIELD_TYPE_BLOB);
    }

}