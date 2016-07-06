package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Provincia;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;

import java.util.List;

@Deprecated

public interface ProvinciaDao extends EntityDao<Provincia> {
    class ProvinciaDAOSQLite extends EntityDaoSQLite<Provincia> implements ProvinciaDao {

        public ProvinciaDAOSQLite(Context context) {
            super(context, "province");
        }

        @Override
        protected Provincia instanceEntity(String[] args) {
            return new Provincia(args[1], Integer.parseInt(args[0]), Integer.parseInt(args[2]));
        }

        @Override
        protected List<String[]> serialize(Provincia entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(Provincia entity) throws DaoException {
            return null;
        }
    }
}