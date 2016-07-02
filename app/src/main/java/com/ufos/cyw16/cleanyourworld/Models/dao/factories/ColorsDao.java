package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Colors;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

@Deprecated

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