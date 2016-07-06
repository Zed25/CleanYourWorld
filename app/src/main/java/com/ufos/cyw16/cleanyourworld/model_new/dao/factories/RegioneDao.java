/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.RegioneDao
 * Last modified: 05/07/16 3.37
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.RegioneDao
 * Last modified: 04/07/16 8.58
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Regione;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface RegioneDao.
 * This interface and her inheritance class allow you to create a RegioneDao object
 */
public interface RegioneDao extends EntityDao<Regione> {

    List<Regione> findAllLazy() throws DaoException;
    /**
     * The type Regione dao sq lite.
     * This class implements the instruction of the RegioneDao and inherits all method of EntityDaoSQLite
     */
    class RegioneDaoSQLite extends EntityDaoSQLite<Regione> implements RegioneDao {


        /**
         * Instantiates a new RegioneDaoSQLite.
         *
         * @param context the context
         */
        public RegioneDaoSQLite(Context context) {
            super(context, "regioni");
        }

        @Override
        protected Regione instanceEntity(String[] args) {
            Regione regione = new Regione();
            regione.setIdRegione_int(Integer.parseInt(args[0]));
            regione.setName(args[1]);
            try {
                regione.setProvince(DaoFactory_def.getInstance(getContext()).getProvinciaDao().getByIdRegion(Integer.parseInt(args[0])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return regione;
        }

        @Override
        protected List<String[]> serialize(Regione entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(Regione entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        protected Regione instanceEntity(String[] args, boolean lazy) {
            Regione regione = new Regione();
            regione.setIdRegione_int(Integer.parseInt(args[0]));
            regione.setName(args[1]);
            try {
                regione.setProvince(DaoFactory_def.getInstance(getContext()).getProvinciaDao().getByIdRegionLazy(Integer.parseInt(args[0])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return regione;
        }

        @Override
        public List<Regione> findAllLazy() throws DaoException {
            ArrayList<Regione> result = new ArrayList<>();
            List<String[]> list = getTableAdapter().getData(null, null, null);
            for (String[] args : list) {
                Message4Debug.log(args.toString());
                result.add(instanceEntity(args, true));
            }
            return result;
        }
    }
}