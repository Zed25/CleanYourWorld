/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ComuneDao
 * Last modified: 04/07/16 8.42
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Comune;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface ComuneDao.
 * This interface and her inheritance class allow you to create a ComuneDao object
 */
public interface ComuneDao extends EntityDao<Comune> {
    /**
     * Gets by id provincia.
     *
     * @param id the id
     * @return the by id provincia
     * @throws DaoException the dao exception
     */
    List<Comune> getByIdProvincia(int id) throws DaoException;

    /**
     * Gets comuni that provide collection.
     *
     * @return the comuni that provide collection
     * @throws DaoException the dao exception
     */
    List<Comune> getComuniThatProvideCollection() throws DaoException;

    /**
     * The type ComuneDaoSQLite.
     * This class implements the instruction of the ComuneDao and inherits all method of EntityDaoSQLite
     */
    class ComuneDaoSQLite extends EntityDaoSQLite<Comune> implements ComuneDao {

        /**
         * Instantiates a new ComuneDaoSQLite.
         *
         * @param context the context
         */
        public ComuneDaoSQLite(Context context) {
            super(context, "comuni");
        }

        @Override
        protected Comune instanceEntity(String[] args) {
            Comune comune = new Comune();
            comune.setIdComune(Integer.parseInt(args[0]));
            comune.setName(args[1]);
            try {
                comune.setIsolaEcologica(DaoFactory_def.getInstance(getContext()).getIsolaEcologicaDao().getByIdComune(Integer.parseInt(args[0])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            try {
                comune.setCollections(DaoFactory_def.getInstance(getContext()).getCollectionDao().getCollectionsByIdComune(Integer.parseInt(args[0])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return comune;
        }

        @Override
        public List<Comune> getByIdProvincia(int id) throws DaoException {
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"province_id"}, new String[]{String.valueOf(id)}, null);
            List<Comune> comuni = new ArrayList<>();
            for (String[] s : resultQuery) {
                comuni.add(instanceEntity(s));
            }
            return comuni;
        }

        @Override
        public List<Comune> getComuniThatProvideCollection() throws DaoException {
            // FIXME: 04/07/16 interrogare lo schema raccolta!
            List<Comune> comuneList = DaoFactory_def.getInstance(getContext()).getComuneDao().findAll();
            List<Comune> result = new ArrayList<>();
            for (Comune c : comuneList) {
                if (c.getCollections() != null) {
                    result.add(c);
                }
            }
            return result;
        }
    }
}