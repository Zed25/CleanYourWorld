/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProvinciaDao
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProvinciaDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Provincia;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

public interface ProvinciaDao extends EntityDao<Provincia> {
    List<Provincia> getByIdRegion(int id) throws DaoException;

    class ProvinciaDAOSQLite extends EntityDaoSQLite<Provincia> implements ProvinciaDao {

        public ProvinciaDAOSQLite(Context context) {
            super(context, "province");
        }

        @Override
        protected Provincia instanceEntity(String[] args) {
            Provincia provincia = new Provincia();
            provincia.setIdProvincia(Integer.parseInt(args[0]));
            provincia.setName(args[1]);
            try {
                provincia.setComuni(DaoFactory_def.getInstance(getContext()).getComuneDao().getByIdProvincia(Integer.parseInt(args[0])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return provincia;
        }

        @Override
        public List<Provincia> getByIdRegion(int id) throws DaoException {
            List<String[]> list = getTableAdapter().getData(new String[]{"regioni_id"}, new String[]{String.valueOf(id)}, null);
            List<Provincia> provinces = new ArrayList<>();
            DaoFactory_def daoFactoryDef = DaoFactory_def.getInstance(getContext());
            for (String[] s : list) {
//                Provincia p = new Provincia();
//                p.setIdProvincia(Integer.parseInt(s[0]));
//                p.setName(s[1]);
//                p.setComuni(daoFactory.getComuneDao().getByIdProvincia(Integer.parseInt(s[0])));
                provinces.add(instanceEntity(s));
            }
            return provinces;
        }
    }
}