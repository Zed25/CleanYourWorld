package com.ufos.cyw16.cleanyourworld.dal.dao;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.InsertDaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.UpdateDaoException;

import java.util.List;

/**
 * The interface EntityDao.
 * This interface defines the main methods for each class Dao.
 *
 * @param <T> the type parameter
 */
public interface EntityDao<T> {
    /**
     * Update T entity from the server.
     * This method that updates the internal database by requiring the latest version to the server.
     *
     * @param keys   the keys
     * @param values the values
     * @throws DaoException         the dao exception
     * @throws InterruptedException the interrupted exception
     */
    void updateFromServer(String[] keys, String[] values) throws DaoException, InterruptedException;

    /**
     * Find T entity by id.
     * This method searches for a single item in the database by its id
     * and instantiates the object entity.
     *
     * @param id the id
     * @return the t
     * @throws DaoException the dao exception
     */
    T findById(int id) throws DaoException;

    /**
     * Find all T enities.
     * This method searches for all items in the database.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Insert T entity.
     * This method saves an object entity into daatabase
     *
     * @param entity the entity
     * @return the t
     */
    T insert(T entity) throws DaoException, InsertDaoException;

    /**
     * Delete T entity.
     * This method removes an object entity from the database.
     *
     * @param entity the entity
     */
    void delete(T entity);

    /**
     * Update T.
     * This method update on object entity from the database
     *
     * @param entity the entity
     * @return the t
     */
    T update(T entity) throws DaoException, UpdateDaoException;

    /**
     * Insert or update T.
     * This method inserts or updates on object entity from the database
     *
     * @param entity the entity
     * @return the t
     */
    T insertOrUpdate(T entity) throws DaoException;

}
