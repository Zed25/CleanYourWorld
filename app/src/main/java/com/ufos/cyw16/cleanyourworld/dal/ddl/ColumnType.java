/*
 * Created by Umberto Ferracci from urania on 04/06/16 18.06
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.ColumnType
 * File name: ColumnType.java
 * Class name: ColumnType
 * Last modified: 03/06/16 19.41
 */

/*
 * Created by Umberto Ferracci from urania on 03/06/16 0.03
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.ddl.TypeColumn
 * File name: TypeColumn.java
 * Class name: TypeColumn
 * Last modified: 03/06/16 0.03
 */

package com.ufos.cyw16.cleanyourworld.dal.ddl;

import java.util.HashMap;

public class ColumnType {
    public static final int FIELD_TYPE_NULL = 0;
    public static final int FIELD_TYPE_INTEGER = 1;
    public static final int FIELD_TYPE_FLOAT = 2;
    public static final int FIELD_TYPE_STRING = 3;
    public static final int FIELD_TYPE_BLOB = 4;
    private static HashMap<String, Integer> hashMaps;

    public static int getTypeCode(String fieldType) {
        hashMapCalulate();
        return hashMaps.get(fieldType).intValue();
    }

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
