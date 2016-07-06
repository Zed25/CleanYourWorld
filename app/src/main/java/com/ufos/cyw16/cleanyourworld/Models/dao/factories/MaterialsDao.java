package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Materials;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;

import java.util.List;

@Deprecated
public interface MaterialsDao extends EntityDao<Materials> {
    class MaterialsDaoSQLite extends EntityDaoSQLite<Materials> implements MaterialsDao {

        public MaterialsDaoSQLite(Context context) {
            super(context, "materiali");
        }

        @Override
        protected Materials instanceEntity(String[] args) {
            return new Materials(Integer.parseInt(args[0]), args[1]);
        }

        @Override
        protected List<String[]> serialize(Materials entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(Materials entity) throws DaoException {
            return null;
        }
    }
}