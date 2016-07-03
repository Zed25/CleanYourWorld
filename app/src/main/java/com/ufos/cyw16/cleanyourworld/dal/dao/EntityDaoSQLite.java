package com.ufos.cyw16.cleanyourworld.dal.dao;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter_NEW;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

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

    @Override
    public void updateFromServer(String[] keys, String[] values) throws DaoException, InterruptedException {
        getTableAdapter().updateFromServer(keys, values);
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

    @Override
    public T findById(int id) throws DaoException {
        List<String[]> list = getTableAdapter().getData(new String[]{"_id"}, new String[]{String.valueOf(id)}, null);
        if (list.size() < 1)
            throw new DaoException("Nothing elements in table " + getTableAdapter().getTableName() + " WHERE <_id = " + id + ">");
        return instanceEntity(list.get(0));
    }

    @Override
    public List<T> findAll() throws DaoException {
        return find(null, null, null);
    }

    @Override
    public T insert(T entity) {
        // TODO: 03/07/16  
        return null;
    }

    @Override
    public void delete(T entity) {
        // TODO: 03/07/16
    }

    @Override
    public T update(T entity) {
        // TODO: 03/07/16
        return null;
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
            Message4Debug.log(args.toString());
            result.add(instanceEntity(args));
        }
        return result;
    }
}
