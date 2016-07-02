/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.RegioneDao
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.RegioneDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Regione;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

public interface RegioneDao extends EntityDao<Regione> {
    class RegioneDaoSQLite extends EntityDaoSQLite<Regione> implements RegioneDao {


        public RegioneDaoSQLite(Context context) {
            super(context, "regioni");
        }

        @Override
        protected Regione instanceEntity(String[] args) {
            Regione regione = new Regione();
            regione.setIdRegione_int(Integer.parseInt(args[0]));
            regione.setName(args[1]);
            try {
                regione.setProvince(DaoFactory_def.getInstance(getContext()).getProvinciaDao().getByIdRegion(Integer.parseInt(args[0])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return regione;
        }
    }
}