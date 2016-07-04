/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionDao
 * Last modified: 04/07/16 8.58
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Collection;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface CollectionDao.
 * This interface and her inheritance class allow you to create a CollectionDao object
 */
public interface CollectionDao extends EntityDao<Collection> {
    /**
     * Gets collection by day of week.
     *
     * @param comuneID  the comune id
     * @param dayOfWeek the day of week
     * @return the collection by day of week
     * @throws DaoException the dao exception
     */
    List<Collection> getCollectionByDayOfWeek(int comuneID, int dayOfWeek) throws DaoException;

    /**
     * Gets collections by id comune.
     *
     * @param id the id
     * @return the collections by id comune
     * @throws DaoException the dao exception
     */
    List<Collection> getCollectionsByIdComune(int id) throws DaoException;

    /**
     * The type CollectionDaoSQLite.
     * This class implements the instruction of the CollectionDao and inherits all method of EntityDaoSQLite
     */
    class CollectionDaoSQLite extends EntityDaoSQLite<Collection> implements CollectionDao {

        /**
         * Instantiates a new CollectionDaoSQLite
         *
         * @param context the context
         */
        public CollectionDaoSQLite(Context context) {
            super(context, "raccolta");
        }

        @Override
        protected Collection instanceEntity(String[] args) {
            Collection collection = new Collection();
            collection.setIdCollection(Integer.parseInt(args[0]));
            try {
                collection.setMaterial(DaoFactory_def.getInstance(getContext()).getMaterialDao().findById(Integer.parseInt(args[2]))); // List<Material>
                collection.setDay(DaoFactory_def.getInstance(getContext()).getDayDao().findById(Integer.parseInt(args[3])));
                collection.setColor(DaoFactory_def.getInstance(getContext()).getColorDao().findById(Integer.parseInt(args[4])));
                collection.setCollectionType(DaoFactory_def.getInstance(getContext()).getCollectionTypeDao().findById(Integer.parseInt(args[5])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return collection;
        }

        @Override
        public List<Collection> getCollectionByDayOfWeek(int comuneID, int dayOfWeek) throws DaoException {
            List<String[]> queryResult = getTableAdapter().getData(new String[]{"comuni_id", "giorni_id"}, new String[]{String.valueOf(comuneID), String.valueOf(dayOfWeek)}, null);
            List<Collection> collections = new ArrayList<>();
            for (String[] s : queryResult) {
                collections.add(instanceEntity(s));
            }
            return collections;
        }

        @Override
        public List<Collection> getCollectionsByIdComune(int id) throws DaoException {
            List<String[]> queryResult = getTableAdapter().getData(new String[]{"comuni_id"}, new String[]{String.valueOf(id)}, null);
            List<Collection> collections = new ArrayList<>();
            for (String[] s : queryResult) {
                collections.add(instanceEntity(s));
            }
            return collections;
        }
    }
}