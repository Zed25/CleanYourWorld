package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.List;

public interface MaterialDao extends EntityDao<Material> {
    class MaterialsDaoSQLite extends EntityDaoSQLite<Material> implements MaterialDao {

        public MaterialsDaoSQLite(Context context) {
            super(context, "materiali");
        }

        @Override
        protected Material instanceEntity(String[] args) {
            Material material = new Material();
            material.setIdMaterial(Integer.parseInt(args[0]));
            material.setName(args[1]);
            // TODO: 01/07/16 how do i get the ProductType list avoiding the loop? [Risolto - non cancellare]
            /*
             * Implementazione della RELAZIONE DI AGGREGAZIONE:
             * devo passargli me stesso
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
    }
}