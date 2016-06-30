

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Collection;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

public interface CollectionDao extends EntityDao<Collection> {
    class CollectionDaoSQLite extends EntityDaoSQLite<Collection> implements CollectionDao {

        public CollectionDaoSQLite(Context context) {
            super(context, "raccolta");
        }

        @Override
        protected Collection instanceEntity(String[] args) {
            return new Collection(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
        }
    }
}