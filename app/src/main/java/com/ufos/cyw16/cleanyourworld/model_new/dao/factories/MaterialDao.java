/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.MaterialDao
 * Last modified: 04/07/16 8.56
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Collection;
import com.ufos.cyw16.cleanyourworld.model_new.Color;
import com.ufos.cyw16.cleanyourworld.model_new.Day;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface MaterialDao.
 * This interface and her inheritance class allow you to create a MaterialDao object
 */
public interface MaterialDao extends EntityDao<Material> {
    /**
     * Gets materials from id comune.
     *
     * @param id the id
     * @return the materials from id comune
     */
    List<Material> getMaterialsFromIdComune(int id) throws DaoException;

    /**
     * The type MaterialsDaoSQLite.
     * This class implements the instruction of the MaterialDao and inherits all method of EntityDaoSQLite
     */
    class MaterialsDaoSQLite extends EntityDaoSQLite<Material> implements MaterialDao {

        /**
         * Instantiates a new MaterialsDaoSQLite.
         *
         * @param context the context
         */
        public MaterialsDaoSQLite(Context context) {
            super(context, "materiali");
        }

        @Override
        protected Material instanceEntity(String[] args) {
            Material material = new Material();
            material.setIdMaterial(Integer.parseInt(args[0]));
            material.setName(args[1]);
            /*
             * Implementazione della RELAZIONE DI AGGREGAZIONE:
             */
            List<ProductType> productTypes = null;
            try {
                productTypes = DaoFactory_def.getInstance(getContext()).getProtuctTypeDao().getProductsByIdMaterial(Integer.parseInt(args[0]), material);
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            material.setProdutctTypes(productTypes);
            return material;
        }

        @Override
        public List<Material> getMaterialsFromIdComune(int id) throws DaoException {

            List<Collection> collections = DaoFactory_def.getInstance(getContext()).getCollectionDao().getCollectionsByIdComune(id);
            if (collections == null)
                throw new DaoException("This Comune does not separate trash");
            List<Material> materials = new ArrayList<>();
            List<Integer> ids = new ArrayList<>();
            for (Collection c : collections) {
                List<Day> days = new ArrayList<>();
                Material material = c.getMaterial();
                if (!ids.contains(material.getIdMaterial())) {
                    material.setDays(days);
                    material.getDays().add(c.getDay());
                    material.setColor(c.getColor());
                    ids.add(material.getIdMaterial());
                    materials.add(material);
                }else{
                    for(Material m: materials){
                        if(c.getMaterial().getName().equals(m.getName())){
                            m.getDays().add(c.getDay());
                            break;
                        }
                    }
                }
            }
            return materials;
        }
    }
}