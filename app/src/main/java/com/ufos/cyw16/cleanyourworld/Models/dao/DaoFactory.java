
package com.ufos.cyw16.cleanyourworld.Models.dao;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionTypeDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ColorsDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.MaterialsDao;

@Deprecated
public class DaoFactory {
    private static DaoFactory instance = null;
    private Context context;

    private DaoFactory(Context context) {
        this.context = context;
    }

    public synchronized static final DaoFactory getInstance(Context context) {
        if (instance == null)
            instance = new DaoFactory(context);
        return instance;
    }


    public CollectionTypeDao getCollectionTypeDao() {
        return new CollectionTypeDao.CollectionTypeDaoSQLite(context);
    }

    public ColorsDao getColorsDao() {
        return new ColorsDao.ColorsDaoSQLite(context);
    }



    public MaterialsDao getMaterialsDao() {
        return new MaterialsDao.MaterialsDaoSQLite(context);
    }

}
