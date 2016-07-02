/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.DayDao
 * Last modified: 30/06/16 12.15
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionDao
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionDao
 * Last modified: 30/06/16 10.34
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.model_new.Day;

public interface DayDao extends EntityDao<Day> {
    class DayDaoSQLite extends EntityDaoSQLite<Day> implements DayDao {

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