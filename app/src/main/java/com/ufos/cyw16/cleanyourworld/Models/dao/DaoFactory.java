/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.DaoFactory
 * File name: DaoFactory.java
 * Class name: DaoFactory
 * Last modified: 25/06/16 19.54
 */

package com.ufos.cyw16.cleanyourworld.Models.dao;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionTypeDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ColorsDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ComuneDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.IsolaEcologicaDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.MaterialsDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProductTypeDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProductsDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProvinciaDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.RegioneDao;

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

    public CollectionDao getCollectionDao() {
        return new CollectionDao.CollectionDaoSQLite(context);
    }

    public CollectionTypeDao getCollectionTypeDao() {
        return new CollectionTypeDao.CollectionTypeDaoSQLite(context);
    }

    public ColorsDao getColorsDao() {
        return new ColorsDao.ColorsDaoSQLite(context);
    }

    public ComuneDao getComuneDao() {
        return new ComuneDao.ComuneDaoSQLite(context);
    }

    public IsolaEcologicaDao getIsolaEcologicaDao() {
        return new IsolaEcologicaDao.IsolaEcologicaDaoSQLite(context);
    }

    public MaterialsDao getMaterialsDao() {
        return new MaterialsDao.MaterialsDaoSQLite(context);
    }

    public ProductsDao getProductsDao() {
        return new ProductsDao.ProductsDaoSQLite(context);
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
