/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductTypeDao
 * Last modified: 30/06/16 11.57
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProductTypeDao
 * Last modified: 26/06/16 1.55
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

public interface ProductTypeDao extends EntityDao<ProductType> {
    List<ProductType> getProductsByIdMaterial(int id) throws DaoException;

    List<ProductType> getProductsByIdMaterial(int id, Material material) throws DaoException;

    class ProductTypeDaoSQLite extends EntityDaoSQLite<ProductType> implements ProductTypeDao {

        public ProductTypeDaoSQLite(Context context) {
            super(context, "tipologiaProdotti");
        }

        @Override
        protected ProductType instanceEntity(String[] args) {
            /*
             * costruttore utilizzato per la relazione di composizione
             */
            ProductType productType = null;
            try {
                productType = instanceEntity(args, DaoFactory_def.getInstance(getContext()).getMaterialDao().findById(Integer.parseInt(args[2])));
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return productType;
        }

        @Override
        public List<ProductType> getProductsByIdMaterial(int id) throws DaoException {
            /*
             * RELAZIONE DI COMPOSIZIONE:
             * interrogo il database
             * instanzio il primo costruttore
             * il tipo Material si estrae dal database
             */
            return null;
        }


        protected ProductType instanceEntity(String[] args, Material material) {
            /*
             * costruttore utilizzato per la relazione di aggregazione
             */
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

        public List<ProductType> getProductsByIdMaterial(int id, Material material) throws DaoException {
            /*
             * RELAZIONE DI AGGREGAZIONE:
             * non interrogo il database ma
             * instanzio il secondo costruttore
             * passandogli il Material da inserire nell'oggetto;
             */
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"materiali_id"}, new String[]{String.valueOf(id)}, null);
            List<ProductType> products = new ArrayList<>();
            for (String[] s : resultQuery) {
                products.add(instanceEntity(s, material));
            }
            return products;
        }
    }
}