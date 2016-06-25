/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao
 * File name: EntityDao.java
 * Class name: EntityDao
 * Last modified: 25/06/16 18.11
 */

package com.ufos.cyw16.cleanyourworld.dal.dao;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;

import java.util.List;

/**
 *
 */
public interface EntityDao<T> {
    List<T> findFromServer(String[] keys, String[] values) throws DaoException, InterruptedException;

    T findById(int id) throws DaoException;

    List<T> findAll() throws DaoException;

    T insert(T entity);

    void delete(T entity);

    T update(T entity);

}
