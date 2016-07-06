/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite
 * Last modified: 05/07/16 4.58
 */

package com.ufos.cyw16.cleanyourworld.dal.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.InsertDaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter_NEW;
import com.ufos.cyw16.cleanyourworld.dal.dml.UpdateDaoException;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


/**
 * The type EntityDaoSQLite.
 * Abstract class that implement EntityDao's interface
 * and uses TableAdapter's methods for to get data from database
 * and creates the objects entity.
 *
 * @param <T> the type parameter of Entity class
 */
public abstract class EntityDaoSQLite<T> implements EntityDao<T> {
    private final Context context;
    private Class<T> persistentClass;
    private TableAdapter_NEW tableAdapter;
    private long timeUpdate = 1 * 60 * 60 * 1000;

    /**
     * Instantiates a new EntityDaoSQLite.
     *
     * @param context   the context
     * @param tableName the table name
     */
    public EntityDaoSQLite(Context context, String tableName) {
        persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // istanzio la classe di tipo entity;
        tableAdapter = new TableAdapter_NEW(context, tableName);
        this.context = context;

    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Gets tableAdapter.
     *
     * @return the tableAdapter
     */
    protected final TableAdapter_NEW getTableAdapter() {
        return tableAdapter;
    }

    /**
     * Sets tableAdapter.
     *
     * @param tableAdapter the tableAdapter
     */
    protected final void setTableAdapter(TableAdapter_NEW tableAdapter) {
        this.tableAdapter = tableAdapter;
    }

    /**
     * Find entity classes into database
     *
     * @param selectionClauses the selection clauses
     * @param selectionArgs    the selection args
     * @param orderBy          the order by
     * @return the list of entity
     * @throws DaoException the dao exception
     */
    public List<T> find(String[] selectionClauses, String[] selectionArgs, String orderBy) throws DaoException {
        ArrayList<T> result = new ArrayList<>();
        List<String[]> list = getTableAdapter().getData(selectionClauses, selectionArgs, orderBy);
        for (String[] args : list) {
            result.add(instanceEntity(args));
        }
        return result;
    }

    @Override
    public T findById(int id) throws DaoException {
        List<String[]> list = getTableAdapter().getData(new String[]{"_id"}, new String[]{String.valueOf(id)}, null);
        if (list.size() < 1)
            throw new DaoException("Nothing elements in table " + getTableAdapter().getTableName() + " WHERE <_id = " + id + ">");
        return instanceEntity(list.get(0));
    }

    @Override
    public List<T> findAll() throws DaoException {
        List<T> find = find(null, null, null);
        if (find == null)
            throw new DaoException("");
        return find;
    }

    @Override
    public T insert(T entity) throws InsertDaoException, DaoException {
        List<String[]> serialization = serialize(entity);
        if (serialization.size() != 2)
            throw new DaoException("Impossible to insert the object into database. <keys, values>");
        getTableAdapter().insert(serialization.get(0), serialization.get(1));
        return entity;
    }

    @Override
    public T insertOrUpdate(T entity) throws DaoException {
        try {
            insert(entity);
        } catch (InsertDaoException e) {
            try {
                update(entity);
            } catch (UpdateDaoException e1) {
                throw new DaoException("Update of the the object" + entity.getClass().getSimpleName() + " in table " + tableAdapter.getTableName() + " failed");
            }
        }
        return entity;
    }

    @Override
    public void delete(T entity) {
        // TODO: 03/07/16
    }

    @Override
    public T update(T entity) throws DaoException, UpdateDaoException {
        List<String[]> serialization = serializeForUpdate(entity);
        if (serialization.size() != 4)
            throw new DaoException("Impossible to update the object into database. <keys, newValues, whereClause, whereArgs>");
        String[] key, newValues, whereClauses, whereArgs;
        key = serialization.get(0);
        newValues = serialization.get(1);
        whereClauses = serialization.get(2);
        whereArgs = serialization.get(3);
        getTableAdapter().update(key, newValues, whereClauses, whereArgs);
        return entity;
    }

    @Override
    public void updateFromServer(String[] keys, String[] values) throws DaoException, InterruptedException {
        SharedPreferences sharedPref = context.getSharedPreferences("CYW", Context.MODE_PRIVATE);
        long lastUpdate = sharedPref.getLong(getTableAdapter().getTableName(), 0L);
        if ((System.currentTimeMillis() - lastUpdate) > timeUpdate) {
            getTableAdapter().updateFromServer(keys, values);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong(getTableAdapter().getTableName(), System.currentTimeMillis()).commit();
        }
    }

    /**
     * Instance entity T.
     * abstract method that instantiates the entity class
     *
     * @param args the args
     * @return the T entity
     */
    protected abstract T instanceEntity(String[] args);

    /**
     * Serialize entity T.
     * abstract method that serialize the entity class
     *
     * @param entity the entity
     * @return the T entity
     */
    protected abstract List<String[]> serialize(T entity) throws DaoException;

    /**
     * Serialize for upadte entity T.
     * abstract method that serialize the entity class
     *
     * @param entity the entity
     * @return the T entity
     */
    protected abstract List<String[]> serializeForUpdate(T entity) throws DaoException;
}
