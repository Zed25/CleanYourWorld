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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufos.cyw16.cleanyourworld.Models.Collection;
import com.ufos.cyw16.cleanyourworld.Models.CollectionType;
import com.ufos.cyw16.cleanyourworld.Models.Colors;
import com.ufos.cyw16.cleanyourworld.Models.Materials;
import com.ufos.cyw16.cleanyourworld.dal.ddl.CYWOpenHelper;

import java.util.ArrayList;

/**
 * Created by ovidiudanielbarba on 28/06/16.
 */
@Deprecated
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

        System.out.println("GETCOLLECTION BY DAY OF WEEK");
        CYWOpenHelper openHelper = CYWOpenHelper.getInstance(context);
        SQLiteDatabase database = openHelper.getReadableDatabase();

        String[] columns = {ID_COLUMN,COLLECTION_COMUNE_COLUMN,COLLECTION_MATERIALS_COLUMN,COLLECTION_DAYS_COLUMN,COLLECTION_COLORS_COLUMN,COLLECTION_COLLTYPE_COLUMN};

        // get raccolta by comune_id and day of week
        String query = "SELECT * FROM raccolta WHERE comuni_id = ? AND giorni_id = ?";
        Cursor c = database.rawQuery(query,new String[]{String.valueOf(comuneID), String.valueOf(dayOfWeek)});


        // array of materials (there could be more than 1 in a day)
        ArrayList<Materials> materials = new ArrayList<>();
        //materials.add(new Materials(1,"Plastica"));
        // array of colors. colors[i] correspends to materials[i]
        ArrayList<Colors> colors = new ArrayList<>();
        //colors.add(new Colors(1,"blue","#2196f3"));
        //  collection type..is unique for all materials
        CollectionType collectionType = new CollectionType(2,"porta a porta");
        int id = 0;
        int material_id = 1,color_id = 1,collType_id = 2;

        if(c.moveToFirst()){
            do {

                // get all ID's to use in further queries
                id = c.getInt(0);
                material_id = c.getInt(2);
                color_id = c.getInt(4);
                collType_id = c.getInt(5);

                System.out.println("ID :" + id+" MAT_ID :" + material_id);

                 /* inner query to find materials */
                Cursor cursor = database.rawQuery("SELECT * FROM materiali WHERE _id = ?",new String[]{String.valueOf(material_id)});
                if(cursor.moveToFirst()){

                    Materials material = new Materials(cursor.getInt(0), cursor.getString(1));
                    materials.add(material);
                    System.out.println("MATERIALI DAO MATERIALS " + material.getName());

                }

                cursor.close();


                /* inner query to find color */
                Cursor colorCur = database.rawQuery("SELECT * FROM colori WHERE _id = ?",new String[]{String.valueOf(color_id)});
                if(colorCur.moveToFirst()){
                    Colors color = new Colors(colorCur.getInt(0), colorCur.getString(1), colorCur.getString(2));
                    colors.add(color);
                    System.out.println("MATERIALI DAO : COLOR " + color.getColor() + " " + color.getColorCode());

                }

                colorCur.close();


                /* inner query to find collection type */
                Cursor collTypeCur = database.rawQuery("SELECT * from tipologiaRaccolta WHERE _id = ?",new String[]{String.valueOf(collType_id)});
                if(collTypeCur.moveToFirst()){
                    collectionType = new CollectionType(collTypeCur.getInt(0),collTypeCur.getString(1));
                }

                collTypeCur.close();

            } while (c.moveToNext());

        }

        // close the cursor to reuse it later
        c.close();



        // close db to remove unused resource
        database.close();


//        return new Collection(id,comuneID,dayOfWeek,materials,colors,collectionType.getName());
        return null;


    }


}
