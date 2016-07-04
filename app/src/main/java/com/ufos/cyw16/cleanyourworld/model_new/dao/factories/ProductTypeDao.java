/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductTypeDao
 * Last modified: 04/07/16 8.51
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface ProductTypeDao.
 * This interface and her inheritance class allow you to create a ProductTypeDao object
 */
public interface ProductTypeDao extends EntityDao<ProductType> {
    /**
     * Gets products by id material.
     *
     * @param id the id
     * @return the products by id material
     * @throws DaoException the dao exception
     */
    List<ProductType> getProductsByIdMaterial(int id) throws DaoException;

    /**
     * Gets products by id material.
     *
     * @param id       the id
     * @param material the material
     * @return the products by id material
     * @throws DaoException the dao exception
     */
    List<ProductType> getProductsByIdMaterial(int id, Material material) throws DaoException;

    /**
     * The type Product type dao sq lite.
     * This class implements the instruction of the ProductTypeDao and inherits all method of EntityDaoSQLite
     */
    class ProductTypeDaoSQLite extends EntityDaoSQLite<ProductType> implements ProductTypeDao {

        /**
         * Instantiates a new ProductTypeDaoSQLite.
         *
         * @param context the context
         */
        public ProductTypeDaoSQLite(Context context) {
            super(context, "tipologiaProdotti");
        }

        @Override
        protected ProductType instanceEntity(String[] args) {
            /* RELAZIONE DI COMPOSIZIONE */
            ProductType productType = null;
            try {
                productType = instanceEntity(args, DaoFactory_def.getInstance(getContext()).getMaterialDao().findById(Integer.parseInt(args[2])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return productType;
        }

        /**
         * Instance entity product type.
         *
         * @param args     the args
         * @param material the material
         * @return the product type
         */
        protected ProductType instanceEntity(String[] args, Material material) {
            /* RELAZIONE DI AGGREGAZIONE */
            ProductType productType = new ProductType();
            productType.setIdProductType(Integer.parseInt(args[0]));
            productType.setName(args[1]);
            productType.setMaterial(material);
            try {
                productType.setProducts(DaoFactory_def.getInstance(getContext()).getProductDao().getProdutsByIdProductType(Integer.parseInt(args[0]), productType));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return productType;
        }

        @Override
        public List<ProductType> getProductsByIdMaterial(int id) throws DaoException {
            /* implementazione RELAZIONE DI COMPOSIZIONE */
            // FIXME: 04/07/16
            return null;
        }

        public List<ProductType> getProductsByIdMaterial(int id, Material material) throws DaoException {
            /* implementazione RELAZIONE DI AGGREGAZIONE */
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"materiali_id"}, new String[]{String.valueOf(id)}, null);
            List<ProductType> products = new ArrayList<>();
            for (String[] s : resultQuery) {
                products.add(instanceEntity(s, material));
            }
            return products;
        }
    }
}