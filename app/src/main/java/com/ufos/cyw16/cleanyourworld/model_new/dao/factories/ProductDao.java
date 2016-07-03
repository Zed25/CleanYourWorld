/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductDao
 * Last modified: 03/07/16 18.29
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductsDao
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProductsDao
 * Last modified: 26/06/16 1.55
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
 * The interface Product dao.
 */
public interface ProductDao extends EntityDao<Product> {
    /**
     * Gets produts by id product type.
     *
     * @param id the id
     * @return the produts by id product type
     */
    List<Product> getProdutsByIdProductType(int id);

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
     * The type Products dao sq lite.
     */
    class ProductsDaoSQLite extends EntityDaoSQLite<Product> implements ProductDao {

        /**
         * Instantiates a new Products dao sq lite.
         *
         * @param context the context
         */
        public ProductsDaoSQLite(Context context) {
            super(context, "prodotti");
        }

        @Override
        protected Product instanceEntity(String[] args) {
            /*
             * costruttore utilizzato per la relazione di composizione
             */
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
            /*
             * costruttore utilizzato per la relazione di aggregazione
             */
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
            /*
             * RELAZIONE DI COMPOSIZIONE:
             * interrogo il database
             * instanzio il primo costruttore
             * il tipo ProductType si estrae dal database
             */
            List<Product> products = null;
            try {
                products = getProdutsByIdProductType(id, DaoFactory_def.getInstance(getContext()).getProtuctTypeDao().findById(id));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return products;
        }

        @Override
        public List<Product> getProdutsByIdProductType(int id, ProductType productType) throws DaoException {
            /*
             * RELAZIONE DI AGGREGAZIONE:
             * non interrogo il database per ottenere ProductType ma
             * instanzio il secondo costruttore
             * passandogli il ProductType da inserire nell'oggetto;
             */
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"tipologiaProdotti_id"}, new String[]{String.valueOf(id)}, null);
            List<Product> products = new ArrayList<>();
            for (String[] s : resultQuery) {
                products.add(instanceEntity(s, productType));
            }
            return products;
        }
    }
}