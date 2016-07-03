/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ComuneDao
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ComuneDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Comune;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

public interface ComuneDao extends EntityDao<Comune> {
    List<Comune> getByIdProvincia(int id) throws DaoException;

    List<Comune> getComuniThatProvideCollection() throws DaoException;

    class ComuneDaoSQLite extends EntityDaoSQLite<Comune> implements ComuneDao {

        public ComuneDaoSQLite(Context context) {
            super(context, "comuni");
        }

        @Override
        protected Comune instanceEntity(String[] args) {
            Comune comune = new Comune();
            comune.setIdComune(Integer.parseInt(args[0]));
            comune.setName(args[1]);
            try {
                comune.setIsolaEcologica(DaoFactory_def.getInstance(getContext()).getIsolaEcologicaDao().getByIdComune(Integer.parseInt(args[0])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            // FIXME: 01/07/16 get collection [Risolto - non cancellare]
            return comune;
        }

        @Override
        public List<Comune> getByIdProvincia(int id) throws DaoException {
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"province_id"}, new String[]{String.valueOf(id)}, null);
            List<Comune> comuni = new ArrayList<>();
            for (String[] s : resultQuery) {
                comuni.add(instanceEntity(s));
            }
            return comuni;
        }

        @Override
        public List<Comune> getComuniThatProvideCollection() throws DaoException {
            List<Comune> comuni = findAll();
            List<Comune> result = new ArrayList<>();
            for (Comune c : comuni) {
                if (c.getCollections() != null) ;
                result.add(c);
            }
            return null;
        }
    }
}