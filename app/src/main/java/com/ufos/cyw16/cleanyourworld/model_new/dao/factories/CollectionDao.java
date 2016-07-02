/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionDao
 * Last modified: 30/06/16 11.57
 */

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

public interface CollectionDao extends EntityDao<Collection> {

    List<Collection> getCollectionsByIdComune(int id) throws DaoException;

    class CollectionDaoSQLite extends EntityDaoSQLite<Collection> implements CollectionDao {

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