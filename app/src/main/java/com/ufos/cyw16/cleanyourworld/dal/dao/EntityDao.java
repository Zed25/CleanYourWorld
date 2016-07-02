

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.dal.dao;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;

import java.util.List;

/**
 *
 */
public interface EntityDao<T> {
    List<T> updateFromServer(String[] keys, String[] values) throws DaoException, InterruptedException;

    T findById(int id) throws DaoException;

    List<T> findAll() throws DaoException;

    T insert(T entity);

    void delete(T entity);

    T update(T entity);

}
