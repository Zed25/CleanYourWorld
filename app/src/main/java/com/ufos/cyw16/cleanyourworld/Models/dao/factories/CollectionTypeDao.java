/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionTypeDao
 * File name: CollectionTypeDao.java
 * Class name: CollectionTypeDao
 * Last modified: 25/06/16 19.57
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