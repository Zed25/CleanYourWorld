/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.DaoFactory
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionTypeDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ColorDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ComuneDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.DayDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.IsolaEcologicaDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.MaterialDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductTypeDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProvinciaDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.RegioneDao;

public class DaoFactory_def {
    private static DaoFactory_def instance = null;
    private Context context;

    private DaoFactory_def(Context context) {
        this.context = context;
    }

    public static final DaoFactory_def getInstance(Context context) {
//        if (instance == null)
        instance = new DaoFactory_def(context);
        return instance;
    }

    public DayDao getDayDao() {
        return new DayDao.DayDaoSQLite(context);
    }

    public CollectionDao getCollectionDao() {
        return new CollectionDao.CollectionDaoSQLite(context);
    }

    public CollectionTypeDao getCollectionTypeDao() {
        return new CollectionTypeDao.CollectionTypeDaoSQLite(context);
    }

    public ColorDao getColorDao() {
        return new ColorDao.ColorsDaoSQLite(context);
    }

    public ComuneDao getComuneDao() {
        return new ComuneDao.ComuneDaoSQLite(context);
    }

    public IsolaEcologicaDao getIsolaEcologicaDao() {
        return new IsolaEcologicaDao.IsolaEcologicaDaoSQLite(context);
    }

    public MaterialDao getMaterialDao() {
        return new MaterialDao.MaterialsDaoSQLite(context);
    }

    public ProductDao getProductDao() {
        return new ProductDao.ProductsDaoSQLite(context);
    }

    public ProductTypeDao getProtuctTypeDao() {
        return new ProductTypeDao.ProductTypeDaoSQLite(context);
    }

    public ProvinciaDao getProvinciaDao() {
        return new ProvinciaDao.ProvinciaDAOSQLite(context);
    }

    public RegioneDao getRegioneDao() {
        return new RegioneDao.RegioneDaoSQLite(context);
    }
}
