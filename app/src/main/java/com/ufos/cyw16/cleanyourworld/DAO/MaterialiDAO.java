/*
 * Created by Umberto Ferracci from ovidiudanielbarba and published on 6/28/16 4:11 PM
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.DAO.MaterialiDAO
 * File name: MaterialiDAO.java
 * Class name: MaterialiDAO
 * Last modified: 6/28/16 4:11 PM
 */

package com.ufos.cyw16.cleanyourworld.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufos.cyw16.cleanyourworld.Models.Collection;
import com.ufos.cyw16.cleanyourworld.Models.Materials;
import com.ufos.cyw16.cleanyourworld.dal.ddl.CYWOpenHelper;

import java.util.ArrayList;

/**
 * Created by ovidiudanielbarba on 28/06/16.
 */
public class MaterialiDAO {

    private Context context;

    private String DATABASE_NAME = "cyw16.db";

    //table names
    private String MATERIALS_TABLE_NAME = "materiali";
    private String COMUNI_TABLE_NAME = "comuni";
    private String PRODUCTS_TYPE_TABLE_NAME = "tipologiaProdotti";
    private String PRODUCTS_TABLE_NAME = "prodotti";
    private String COLLECTION_TYPE_TABLE_NAME = "tipologiaRaccolta";
    private String COLLECTION_TABLE_NAME = "raccolta";
    private String ISOLA_ECOLOGICA_TABLE_NAME = "isolaEcologia";

    private String ID_COLUMN = "_id";
    // MATERIALS column names
    private String MATERIALS_NAME_COLUMN = "nome";

    //Collection column names
    private String COLLECTION_COMUNE_COLUMN = "comuni_id";
    private String COLLECTION_MATERIALS_COLUMN = "materiali_id";
    private String COLLECTION_DAYS_COLUMN = "giorni_id";
    private String COLLECTION_COLORS_COLUMN = "colori_id";
    private String COLLECTION_COLLTYPE_COLUMN = "tipologiaRaccolta_id";


    public MaterialiDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Materials> getAllMaterials(){
        CYWOpenHelper openHelper = CYWOpenHelper.getInstance(context);

        SQLiteDatabase database = openHelper.getReadableDatabase();

        String[] columns = {ID_COLUMN,MATERIALS_NAME_COLUMN};
        Cursor c = database.query(MATERIALS_TABLE_NAME,columns,null,null,null,null,null);

        ArrayList<Materials> materials = new ArrayList<>();
        Materials material = new Materials(0,"");

        if(c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String name = c.getString(1);
                material = new Materials(id,name);

                System.out.println("MATERIALS ID = " + material.getId() + " NAME = " + material.getName());

                materials.add(material);

            }while (c.moveToNext());
        }

        database.close();

        return materials;

    }

    public Collection getCollectionByDayOfWeek(int comuneID,int dayOfWeek){
        CYWOpenHelper openHelper = CYWOpenHelper.getInstance(context);
        SQLiteDatabase database = openHelper.getReadableDatabase();

        String[] columns = {ID_COLUMN,COLLECTION_COMUNE_COLUMN,COLLECTION_MATERIALS_COLUMN,COLLECTION_DAYS_COLUMN,COLLECTION_COLORS_COLUMN,COLLECTION_COLLTYPE_COLUMN};

        String query = "SELECT * FROM raccolta WHERE comuni_id = " + comuneID + " AND giorni_id = " + dayOfWeek;

        Cursor c = database.rawQuery(query,null);

        if(c.moveToFirst()){
            do {

                int id = c.getInt(0);
                int material_id = c.getInt(2);
                int color_id = c.getInt(4);
                int collType_id = c.getInt(5);





            } while (c.moveToNext());

        }

        Collection collection = new Collection(0,0,0,null,"","");

        return collection;

    }


}
