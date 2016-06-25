/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.RegioneDao
 * File name: RegioneDao.java
 * Class name: RegioneDao
 * Last modified: 25/06/16 12.41
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