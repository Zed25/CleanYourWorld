/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ColorsDao
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ColorsDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.model_new.Color;

public interface ColorDao extends EntityDao<Color> {
    class ColorsDaoSQLite extends EntityDaoSQLite<Color> implements ColorDao {

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
    }
}