

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionDao
 * Last modified: 30/06/16 10.34
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Collection;
import com.ufos.cyw16.cleanyourworld.Models.Colors;
import com.ufos.cyw16.cleanyourworld.Models.Materials;
import com.ufos.cyw16.cleanyourworld.Models.dao.DaoFactory;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.List;

public interface CollectionDao extends EntityDao<Collection> {
    class CollectionDaoSQLite extends EntityDaoSQLite<Collection> implements CollectionDao {

        public CollectionDaoSQLite(Context context) {
            super(context, "raccolta");
        }

        @Override
        protected Collection instanceEntity(String[] args) {
            DaoFactory factory = DaoFactory.getInstance(getContext());
            List<Materials> materials = null;
            List<Colors> colors = null;
            String collectionType = "";
            try {
                materials = factory.getMaterialsDao().findAll();
                colors = factory.getColorsDao().findAll();
                collectionType = factory.getCollectionTypeDao().findById(0).getName();
            } catch (DaoException e) {
                e.printStackTrace();
                Message4Debug.log(e.getMessage());
            }
            return new Collection(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), materials, colors, collectionType);
        }
    }
}