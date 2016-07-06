package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.ProductType;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;

import java.util.List;

@Deprecated
public interface ProductTypeDao extends EntityDao<ProductType> {
    class ProductTypeDaoSQLite extends EntityDaoSQLite<ProductType> implements ProductTypeDao {

        public ProductTypeDaoSQLite(Context context) {
            super(context, "tipologiaProdotti");
        }

        @Override
        protected ProductType instanceEntity(String[] args) {
            return new ProductType(Integer.parseInt(args[0]), Integer.parseInt(args[2]), args[1]);
        }

        @Override
        protected List<String[]> serialize(ProductType entity) throws DaoException {
            throw new DaoException("Illegal instruction for table " + getTableAdapter().getTableName());
        }

        @Override
        protected List<String[]> serializeForUpdate(ProductType entity) throws DaoException {
            return null;
        }
    }
}