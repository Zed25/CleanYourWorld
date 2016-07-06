package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Comune;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;

import java.util.List;

@Deprecated
public interface ComuneDao extends EntityDao<Comune> {
    class ComuneDaoSQLite extends EntityDaoSQLite<Comune> implements ComuneDao {

        public ComuneDaoSQLite(Context context) {
            super(context, "comuni");
        }

        @Override
        protected Comune instanceEntity(String[] args) {
            return new Comune(args[1], Integer.parseInt(args[0]), Integer.parseInt(args[2]));
        }

        @Override
        protected List<String[]> serialize(Comune entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(Comune entity) throws DaoException {
            return null;
        }
    }
}