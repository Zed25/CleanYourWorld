/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ColorDao
 * Last modified: 04/07/16 8.58
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Color;

import java.util.List;

/**
 * The interface ColorDao.
 * This interface and her inheritance class allow you to create a ColorDao object
 */
public interface ColorDao extends EntityDao<Color> {
    /**
     * The type ColorsDaoSQLite.
     * This class implements the instruction of the ColorDao and inherits all method of EntityDaoSQLite
     */
    class ColorsDaoSQLite extends EntityDaoSQLite<Color> implements ColorDao {

        /**
         * Instantiates a new ColorsDaoSQLite.
         *
         * @param context the context
         */
        public ColorsDaoSQLite(Context context) {
            super(context, "colori");
        }

        @Override
        protected Color instanceEntity(String[] args) {
            Color color = new Color();
            color.setIdColors(Integer.parseInt(args[0]));
            color.setName(args[1]);
            color.setColorCode(args[2]);
            return color;
        }

        @Override
        protected List<String[]> serialize(Color entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(Color entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

    }
}