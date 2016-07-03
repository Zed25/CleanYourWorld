/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.IsolaEcologicaDao
 * Last modified: 03/07/16 18.29
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.IsolaEcologicaDao
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.IsolaEcologicaDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.IsolaEcologica;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.List;

/**
 * The interface Isola ecologica dao.
 */
public interface IsolaEcologicaDao extends EntityDao<IsolaEcologica> {
    /**
     * Gets by id comune.
     *
     * @param id the id
     * @return the by id comune
     * @throws DaoException the dao exception
     */
    IsolaEcologica getByIdComune(int id) throws DaoException;

    /**
     * The type Isola ecologica dao sq lite.
     */
    class IsolaEcologicaDaoSQLite extends EntityDaoSQLite<IsolaEcologica> implements IsolaEcologicaDao {

        /**
         * Instantiates a new Isola ecologica dao sq lite.
         *
         * @param context the context
         */
        public IsolaEcologicaDaoSQLite(Context context) {
            super(context, "isolaEcologica");
        }

        @Override
        protected IsolaEcologica instanceEntity(String[] args) {
            IsolaEcologica isolaEcologica = new IsolaEcologica();
            isolaEcologica.setIdIsolaEcologica(Integer.parseInt(args[0]));
            isolaEcologica.setIndirizzo(args[2]);
            isolaEcologica.setCordinate(args[3]);
            isolaEcologica.setDescrizione(args[4]);
            return isolaEcologica;
        }

        @Override
        public IsolaEcologica getByIdComune(int id) throws DaoException {
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"comuni_id"}, new String[]{String.valueOf(id)}, null);
            if (id == 1865) {
                Message4Debug.log("");
            }
            if (resultQuery.size() == 0)
                return null;
            return instanceEntity(resultQuery.get(0));
        }
    }
}