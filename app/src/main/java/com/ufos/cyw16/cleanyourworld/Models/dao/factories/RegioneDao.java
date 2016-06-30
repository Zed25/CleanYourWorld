

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.RegioneDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Regione;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

public interface RegioneDao extends EntityDao<Regione> {
    class RegioneDaoSQLite extends EntityDaoSQLite<Regione> implements RegioneDao {


        public RegioneDaoSQLite(Context context) {
            super(context, "regioni");
        }

        @Override
        protected Regione instanceEntity(String[] args) {
            return new Regione(Integer.parseInt(args[0]), args[1]);
        }
    }
}