/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductScanDao
 * Last modified: 05/07/16 17.48
 */

package com.ufos.cyw16.cleanyourworld.model_new.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
import com.ufos.cyw16.cleanyourworld.model_new.ProductScan;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.utlity.Choises;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface ProductScanDao.
 * This interface and her inheritance class allow you to create a ProductScanDao object
 */
public interface ProductScanDao extends EntityDao<ProductScan> {
    void sendToServer(String barcode) throws DaoException;

    ProductScan getProductScanByProducdId(int id) throws DaoException;

    /**
     * The type ProductsDaoSQLite.
     * This class implements the instruction of the ProductScanDao and inherits all method of EntityDaoSQLite
     */
    class ProductScanDaoSQLite extends EntityDaoSQLite<ProductScan> implements ProductScanDao {

        /**
         * Instantiates a new ProductsDaoSQLite.
         *
         * @param context the context
         */
        public ProductScanDaoSQLite(Context context) {
            super(context, "productScan");
        }


        @Override
        protected ProductScan instanceEntity(String[] args) {
            List<Material> materials = null;
            try {
                materials = DaoFactory_def.getInstance(getContext()).getMaterialDao().getMaterialsFromIdComune(Choises.getIdComune());
            } catch (DaoException e) {
                e.printStackTrace();
            }
            ProductScan productScan = new ProductScan();
            productScan.setIdScan(Integer.parseInt(args[0]));
            productScan.setDate(args[2]);
            try {
                productScan.setProduct(DaoFactory_def.getInstance(getContext()).getProductDao().findById(Integer.parseInt(args[1])));
                if (materials != null) {
                    for (Material m : materials) {
                        if (m.getIdMaterial() == productScan.getProduct().getProductType().getMaterial().getIdMaterial()) {
                            productScan.getProduct().getProductType().setMaterial(m);
                            break;
                        }
                    }
                }
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
            return productScan;
        }

        @Override
        protected List<String[]> serialize(ProductScan entity) throws DaoException {
            String[] key = new String[]{"prodotti_id", "date"};
            //Product p;
            //if(( p = entity.getProduct())==null){
            //    throw new DaoException("Elemento non presente");
            //}
            String[] values = new String[]{String.valueOf(entity.getProduct().getIdProduct()), entity.getDate()};
            List<String[]> serialization = new ArrayList<>();
            serialization.add(key);
            serialization.add(values);
            return serialization;
        }

        @Override
        protected List<String[]> serializeForUpdate(ProductScan entity) throws DaoException {
            ProductScan old = getProductScanByProducdId(entity.getProduct().getIdProduct());
            List<String[]> serialization = new ArrayList<>();
            serialization.add(new String[]{"prodotti_id", "date"}); /* keys */
            serialization.add(new String[]{String.valueOf(entity.getProduct().getIdProduct()), entity.getDate()}); /* newValues */
            serialization.add(new String[]{"_id"}); /* whereClause */
            serialization.add(new String[]{String.valueOf(old.getIdScan())}); /* whereArgs */
            return serialization;
        }

        @Override
        public void updateFromServer(String[] keys, String[] values) throws DaoException, InterruptedException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        public void sendToServer(String barcode) throws DaoException {
            String[] keys, values;
            keys = new String[]{"barcode"};
            values = new String[]{barcode};
            try {
                getTableAdapter().sendToServer(keys, values);
            } catch (InterruptedException e) {
                throw new DaoException("404!");
            }
        }

        @Override
        public ProductScan getProductScanByProducdId(int id) throws DaoException {
            List<String[]> resultQuery = getTableAdapter().getData(new String[]{"prodotti_id"}, new String[]{String.valueOf(id)}, null);
            return instanceEntity(resultQuery.get(0));
        }
    }
}