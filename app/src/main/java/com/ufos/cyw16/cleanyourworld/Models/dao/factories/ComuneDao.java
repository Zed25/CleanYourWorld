

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ComuneDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Comune;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

public interface ComuneDao extends EntityDao<Comune> {
    class ComuneDaoSQLite extends EntityDaoSQLite<Comune> implements ComuneDao {

        public ComuneDaoSQLite(Context context) {
            super(context, "comuni");
        }

        @Override
        protected Comune instanceEntity(String[] args) {
            return new Comune(args[1], Integer.parseInt(args[0]), Integer.parseInt(args[2]));
        }
    }
}