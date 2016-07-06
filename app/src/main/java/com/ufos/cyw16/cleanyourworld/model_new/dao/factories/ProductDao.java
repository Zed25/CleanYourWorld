/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductDao
 * Last modified: 04/07/16 8.57
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Product;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface ProductDao.
 * This interface and her inheritance class allow you to create a ProductDao object
 */
public interface ProductDao extends EntityDao<Product> {
    /**
     * Gets produts by id product type.
     *
     * @param id the id
     * @return the produts by id product type
     */
    List<Product> getProdutsByIdProductType(int id);

    Product getProductByBarCode(String barcode) throws DaoException;

    /**
     * Gets produts by id product type.
     *
     * @param id          the id
     * @param productType the product type
     * @return the produts by id product type
     * @throws DaoException the dao exception
     */
    List<Product> getProdutsByIdProductType(int id, ProductType productType) throws DaoException;

    /**
     * The type ProductsDaoSQLite.
     * This class implements the instruction of the ProductDao and inherits all method of EntityDaoSQLite
     */
    class ProductsDaoSQLite extends EntityDaoSQLite<Product> implements ProductDao {

        /**
         * Instantiates a new ProductsDaoSQLite.
         *
         * @param context the context
         */
        public ProductsDaoSQLite(Context context) {
            super(context, "prodotti");
        }

        @Override
        protected Product instanceEntity(String[] args) {
            /* RELAZIONE DI COMPOSIZIONE */
            Product product = null;
            try {
                product = instanceEntity(args, DaoFactory_def.getInstance(getContext()).getProtuctTypeDao().findById(Integer.parseInt(args[4])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return product;
        }

        /**
         * Instance entity product.
         *
         * @param args        the args
         * @param productType the product type
         * @return the product
         */
        protected Product instanceEntity(String[] args, ProductType productType) {
            /* RELAZIONE DI AGGREGAZIONE */
            Product product = new Product();
            product.setIdProduct(Integer.parseInt(args[0]));
            product.setName(args[1]);
            product.setDescription(args[2]);
            product.setEAN(args[3]);
            product.setProductType(productType);
            return product;
        }


        @Override
        public List<Product> getProdutsByIdProductType(int id) {
            /* implementazione RELAZIONE DI COMPOSIZIONE */
            List<Product> products = null;
            try {
                products = getProdutsByIdProductType(id, DaoFactory_def.getInstance(getContext()).getProtuctTypeDao().findById(id));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return products;
        }

        @Override
        public Product getProductByBarCode(String barcode) throws DaoException {
            /* implementazione RELAZIONE DI AGGREGAZIONE */
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"EAN"}, new String[]{barcode}, null);
            List<Product> products = new ArrayList<>();
            return instanceEntity(resultQuery.get(0));
        }

        @Override
        public List<Product> getProdutsByIdProductType(int id, ProductType productType) throws DaoException {
            /* implementazione RELAZIONE DI AGGREGAZIONE */
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"tipologiaProdotti_id"}, new String[]{String.valueOf(id)}, null);
            List<Product> products = new ArrayList<>();
            for (String[] s : resultQuery) {
                products.add(instanceEntity(s, productType));
            }
            return products;
        }

        @Override
        protected List<String[]> serialize(Product entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(Product entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }
    }
}