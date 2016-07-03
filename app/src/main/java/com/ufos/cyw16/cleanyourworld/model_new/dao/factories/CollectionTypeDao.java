/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionTypeDao
 * Last modified: 03/07/16 18.49
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.model_new.CollectionType;

/**
 * The interface CollectionTypeDao.
 * This interface and her inheritance class allow you to create a CollectionTypeDao object
 */
public interface CollectionTypeDao extends EntityDao<CollectionType> {
    /**
     * The type CollectionTypeDaoSQLite.
     * This class implements the instruction of the CollectionTypeDao and inherits all method of EntityDaoSQLite
     */
    class CollectionTypeDaoSQLite extends EntityDaoSQLite<CollectionType> implements CollectionTypeDao {

        /**
         * Instantiates a new CollectionTypeDaoSQLite.
         *
         * @param context the context
         */
        public CollectionTypeDaoSQLite(Context context) {
            super(context, "tipologiaRaccolta");
        }

        @Override
        protected CollectionType instanceEntity(String[] args) {
            CollectionType collectionType = new CollectionType();
            collectionType.setIdCollectionType(Integer.parseInt(args[0]));
            collectionType.setName(args[1]);
            return collectionType;
        }
    }
}