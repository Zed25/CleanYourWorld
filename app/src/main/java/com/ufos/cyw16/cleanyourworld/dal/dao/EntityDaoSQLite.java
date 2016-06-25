/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite
 * File name: EntityDaoSQLite.java
 * Class name: EntityDaoSQLite
 * Last modified: 25/06/16 20.46
 */

package com.ufos.cyw16.cleanyourworld.dal.dao;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter_NEW;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class EntityDaoSQLite<T> implements EntityDao<T> {
    private Class<T> persistentClass;
    private TableAdapter_NEW tableAdapter;

    public EntityDaoSQLite(Context context, String tableName) {
        persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // istanzio la classe di tipo entity;
        tableAdapter = new TableAdapter_NEW(context, tableName);
    }

    protected final TableAdapter_NEW getTableAdapter() {
        return tableAdapter;
    }

    protected final void setTableAdapter(TableAdapter_NEW tableAdapter) {
        this.tableAdapter = tableAdapter;
    }

    @Override
    public List<T> findFromServer(String[] keys, String[] values) throws DaoException, InterruptedException {
        getTableAdapter().updateFromServer(keys, values);
        return find(keys, values, null);
    }

    @Override
    public T findById(int id) throws DaoException {
        List<String[]> list = getTableAdapter().getData(new String[]{"_id"}, new String[]{String.valueOf(id)}, null);
        return instanceEntity(list.get(0));
    }

    @Override
    public List<T> findAll() throws DaoException {
        return find(null, null, null);
    }

    @Override
    public T insert(T entity) {
        return null;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public T update(T entity) {
        return null;
    }

    protected abstract T instanceEntity(String[] args);

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
