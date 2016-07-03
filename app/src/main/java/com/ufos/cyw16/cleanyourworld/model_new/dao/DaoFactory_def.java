/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def
 * Last modified: 03/07/16 18.41
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

/**
 * The type DaoFactory.
 * This class allow you to create an DAOEntiy object,
 * it's based on Factory Method pattern.
 */
public class DaoFactory_def {
    private static DaoFactory_def instance = null;
    private Context context;

    /**
     * Instantiates a new DaoFactory.
     *
     * @param context the context
     */
    private DaoFactory_def(Context context) {
        this.context = context;
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static final DaoFactory_def getInstance(Context context) {
//        if (instance == null)
        instance = new DaoFactory_def(context);
        return instance;
    }

    /**
     * Gets dayDao.
     *
     * @return the day dao
     */
    public DayDao getDayDao() {
        return new DayDao.DayDaoSQLite(context);
    }

    /**
     * Gets collectionDao.
     *
     * @return the collection dao
     */
    public CollectionDao getCollectionDao() {
        return new CollectionDao.CollectionDaoSQLite(context);
    }

    /**
     * Gets collectionTypeDao.
     *
     * @return the collection type dao
     */
    public CollectionTypeDao getCollectionTypeDao() {
        return new CollectionTypeDao.CollectionTypeDaoSQLite(context);
    }

    /**
     * Gets colorDao.
     *
     * @return the color dao
     */
    public ColorDao getColorDao() {
        return new ColorDao.ColorsDaoSQLite(context);
    }

    /**
     * Gets comuneDao.
     *
     * @return the comune dao
     */
    public ComuneDao getComuneDao() {
        return new ComuneDao.ComuneDaoSQLite(context);
    }

    /**
     * Gets isolaEcologicaDao.
     *
     * @return the isola ecologica dao
     */
    public IsolaEcologicaDao getIsolaEcologicaDao() {
        return new IsolaEcologicaDao.IsolaEcologicaDaoSQLite(context);
    }

    /**
     * Gets materialDao.
     *
     * @return the material dao
     */
    public MaterialDao getMaterialDao() {
        return new MaterialDao.MaterialsDaoSQLite(context);
    }

    /**
     * Gets productDao.
     *
     * @return the product dao
     */
    public ProductDao getProductDao() {
        return new ProductDao.ProductsDaoSQLite(context);
    }

    /**
     * Gets protuctTypeDao.
     *
     * @return the protuct type dao
     */
    public ProductTypeDao getProtuctTypeDao() {
        return new ProductTypeDao.ProductTypeDaoSQLite(context);
    }

    /**
     * Gets provinciaDao.
     *
     * @return the provincia dao
     */
    public ProvinciaDao getProvinciaDao() {
        return new ProvinciaDao.ProvinciaDAOSQLite(context);
    }

    /**
     * Gets regioneDao.
     *
     * @return the regione dao
     */
    public RegioneDao getRegioneDao() {
        return new RegioneDao.RegioneDaoSQLite(context);
    }
}
