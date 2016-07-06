package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Collection;
import com.ufos.cyw16.cleanyourworld.Models.CollectionType;
import com.ufos.cyw16.cleanyourworld.Models.Colors;
import com.ufos.cyw16.cleanyourworld.Models.Materials;
import com.ufos.cyw16.cleanyourworld.Models.dao.DaoFactory;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.List;

@Deprecated
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
            List<CollectionType> collectionType = null;
            try {
                materials = factory.getMaterialsDao().findAll();
                colors = factory.getColorsDao().findAll();
                collectionType = factory.getCollectionTypeDao().findAll();
            } catch (DaoException e) {
                e.printStackTrace();
                Message4Debug.log(e.getMessage());
            }
            return new Collection(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), materials, colors, collectionType);
        }

        @Override
        protected List<String[]> serialize(Collection entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(Collection entity) throws DaoException {
            return null;
        }
    }
}