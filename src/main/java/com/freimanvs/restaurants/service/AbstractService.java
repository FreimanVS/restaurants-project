package com.freimanvs.restaurants.service;

import com.freimanvs.restaurants.dao.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Brightsunrise
 * @version 1.0
 */
public class AbstractService<T> implements TheService<T> {
    @Autowired
    private DAO<T> dao;

    @Override
    @Transactional
    public long add(T obj) {
        return dao.add(obj);
    }

    @Override
    @Transactional(readOnly = true)
    public T getById(long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getList() {
        return dao.getList();
    };

    @Override
    @Transactional
    public void deleteById(long id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional
    public void updateById(long id, T obj) {
        dao.updateById(id, obj);
    }
}