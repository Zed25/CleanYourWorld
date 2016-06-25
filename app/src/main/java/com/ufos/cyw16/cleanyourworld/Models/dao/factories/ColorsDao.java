/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ColorsDao
 * File name: ColorsDao.java
 * Class name: ColorsDao
 * Last modified: 25/06/16 19.57
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Colors;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

public interface ColorsDao extends EntityDao<Colors> {
    class ColorsDaoSQLite extends EntityDaoSQLite<Colors> implements ColorsDao {

        public ColorsDaoSQLite(Context context) {
            super(context, "colori");
        }

        @Override
        protected Colors instanceEntity(String[] args) {
            return new Colors(Integer.parseInt(args[0]), args[1], args[2]);
        }
    }
}