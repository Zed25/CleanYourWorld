/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.DayDao
 * Last modified: 03/07/16 19.30
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.model_new.Day;

/**
 * The interface DayDao.
 * This interface and her inheritance class allow you to create a DayDao object.
 */
public interface DayDao extends EntityDao<Day> {
    /**
     * The type DayDaoSQLite.
     * This class implements the instruction of the DayDao and inherits all method of EntityDaoSQLite
     */
    class DayDaoSQLite extends EntityDaoSQLite<Day> implements DayDao {

        /**
         * Instantiates a new DayDaoSQLite.
         *
         * @param context the context
         */
        public DayDaoSQLite(Context context) {
            super(context, "giorni");
        }

        @Override
        protected Day instanceEntity(String[] args) {
            Day day = new Day();
            day.setIdDay(Integer.parseInt(args[0]));
            day.setName(args[1]);
            return day;
        }
    }
}