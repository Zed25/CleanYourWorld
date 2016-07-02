

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite
 * Last modified: 30/06/16 10.34
 */

/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dao.EntityDaoSQLite
 * Last modified: 26/06/16 1.55
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
    private final Context context;
    private Class<T> persistentClass;
    private TableAdapter_NEW tableAdapter;

    public Context getContext() {
        return context;
    }

    public EntityDaoSQLite(Context context, String tableName) {
        persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // istanzio la classe di tipo entity;
        tableAdapter = new TableAdapter_NEW(context, tableName);
        this.context = context;

    }

    protected final TableAdapter_NEW getTableAdapter() {
        return tableAdapter;
    }

    protected final void setTableAdapter(TableAdapter_NEW tableAdapter) {
        this.tableAdapter = tableAdapter;
    }

    @Override
    public List<T> updateFromServer(String[] keys, String[] values) throws DaoException, InterruptedException {
        // FIXME: 02/07/16 [TYPE] public void updateFromServer(...) in quando rallenta l'applicazione
        getTableAdapter().updateFromServer(keys, values);
//        return find(keys, values, null);
        return null;
    }

    @Override
    public T findById(int id) throws DaoException {
        List<String[]> list = getTableAdapter().getData(new String[]{"_id"}, new String[]{String.valueOf(id)}, null);
        if (list.size() < 1)
            throw new DaoException("Nothing elements in " + getTableAdapter().getTableName() + "for <_id = " + id + ">");
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
