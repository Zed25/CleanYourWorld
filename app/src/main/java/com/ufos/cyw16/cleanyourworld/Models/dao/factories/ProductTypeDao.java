/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProductTypeDao
 * File name: ProductTypeDao.java
 * Class name: ProductTypeDao
 * Last modified: 25/06/16 19.57
 */

package com.ufos.cyw16.cleanyourworld.Models.dao.factories;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.Models.ProductType;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite;

public interface ProductTypeDao extends EntityDao<ProductType> {
    class ProductTypeDaoSQLite extends EntityDaoSQLite<ProductType> implements ProductTypeDao {

        public ProductTypeDaoSQLite(Context context) {
            super(context, "tipologiaProdotti");
        }

        @Override
        protected ProductType instanceEntity(String[] args) {
            return new ProductType(Integer.parseInt(args[0]), Integer.parseInt(args[2]), args[1]);
        }
    }
}