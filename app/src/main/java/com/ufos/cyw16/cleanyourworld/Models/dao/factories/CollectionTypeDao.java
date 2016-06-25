
/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionTypeDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.CollectionType;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

public interface CollectionTypeDao extends EntityDao<CollectionType> {
    class CollectionTypeDaoSQLite extends EntityDaoSQLite<CollectionType> implements CollectionTypeDao {

        public CollectionTypeDaoSQLite(Context context) {
            super(context, "tipologiaRaccolta");
        }

        @Override
        protected CollectionType instanceEntity(String[] args) {
            return new CollectionType(Integer.parseInt(args[0]), args[1]);
        }
    }
}