/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.MaterialsDao
 * File name: MaterialsDao.java
 * Class name: MaterialsDao
 * Last modified: 26/06/16 1.48
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Materials;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

public interface MaterialsDao extends EntityDao<Materials> {
    class MaterialsDaoSQLite extends EntityDaoSQLite<Materials> implements MaterialsDao {

        public MaterialsDaoSQLite(Context context) {
            super(context, "materiali");
        }

        @Override
        protected Materials instanceEntity(String[] args) {
            return new Materials(Integer.parseInt(args[0]), args[1]);
        }
    }
}