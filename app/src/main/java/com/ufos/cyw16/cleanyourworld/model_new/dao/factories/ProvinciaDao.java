/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProvinciaDao
 * Last modified: 05/07/16 3.37
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProvinciaDao
 * Last modified: 04/07/16 8.59
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

/**
 * The interface ProvinciaDao.
 * This interface and her inheritance class allow you to create a ProvinciaDao object
 */
public interface ProvinciaDao extends EntityDao<Provincia> {
    List<Provincia> getByIdRegionLazy(int id) throws DaoException;

    /**
     * Gets by id region.
     *
     * @param id the id
     * @return the by id region
     * @throws DaoException the dao exception
     */
    List<Provincia> getByIdRegion(int id) throws DaoException;

    /**
     * The type ProvinciaDaoSQLite.
     * This class implements the instruction of the ProvinciaDao and inherits all method of EntityDaoSQLite
     */
    class ProvinciaDaoSQLite extends EntityDaoSQLite<Provincia> implements ProvinciaDao {

        /**
         * Instantiates a new ProvinciaDAOSQLite.
         *
         * @param context the context
         */
        public ProvinciaDaoSQLite(Context context) {
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
        protected List<String[]> serialize(Provincia entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(Provincia entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        protected Provincia instanceEntity(String[] args, boolean lazy) {
            Provincia provincia = new Provincia();
            provincia.setIdProvincia(Integer.parseInt(args[0]));
            provincia.setName(args[1]);
            try {
                provincia.setComuni(DaoFactory_def.getInstance(getContext()).getComuneDao().getByIdProvinciaLazy(Integer.parseInt(args[0])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return provincia;
        }

        @Override
        public List<Provincia> getByIdRegionLazy(int id) throws DaoException {
            List<String[]> list = getTableAdapter().getData(new String[]{"regioni_id"}, new String[]{String.valueOf(id)}, null);
            List<Provincia> provinces = new ArrayList<>();
            for (String[] s : list) {
                provinces.add(instanceEntity(s, true));
            }
            return provinces;
        }

        @Override
        public List<Provincia> getByIdRegion(int id) throws DaoException {
            List<String[]> list = getTableAdapter().getData(new String[]{"regioni_id"}, new String[]{String.valueOf(id)}, null);
            List<Provincia> provinces = new ArrayList<>();
            for (String[] s : list) {
                provinces.add(instanceEntity(s));
            }
            return provinces;
        }
    }
}