/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProductsDao
 * File name: ProductsDao.java
 * Class name: ProductsDao
 * Last modified: 25/06/16 19.57
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.Products;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

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