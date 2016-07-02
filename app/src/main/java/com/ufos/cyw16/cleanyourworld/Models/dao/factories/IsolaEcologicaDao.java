
package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.IsolaEcologica;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

@Deprecated

public interface IsolaEcologicaDao extends EntityDao<IsolaEcologica> {
    class IsolaEcologicaDaoSQLite extends EntityDaoSQLite<IsolaEcologica> implements IsolaEcologicaDao {

        public IsolaEcologicaDaoSQLite(Context context) {
            super(context, "isolaEcologica");
        }

        @Override
        protected IsolaEcologica instanceEntity(String[] args) {
            return new IsolaEcologica(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], args[3], args[4]);
        }
    }
}