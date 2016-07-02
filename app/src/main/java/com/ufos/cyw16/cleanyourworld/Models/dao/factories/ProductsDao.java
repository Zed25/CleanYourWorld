package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Products;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

@Deprecated

public interface ProductsDao extends EntityDao<Products> {
    class ProductsDaoSQLite extends EntityDaoSQLite<Products> implements ProductsDao {

        public ProductsDaoSQLite(Context context) {
            super(context, "prodotti");
        }

        @Override
        protected Products instanceEntity(String[] args) {
            return new Products(Integer.parseInt(args[0]), Integer.parseInt(args[4]), args[1], args[2], args[3]);
        }
    }
}