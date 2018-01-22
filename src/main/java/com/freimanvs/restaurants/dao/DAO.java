package com.freimanvs.restaurants.dao;

import java.util.List;

/**
 * @author Freiman V.S.
 * @version 1.0
 */
public interface DAO<T> {
    long add(T obj);
    List<T> getList();
    T getById(long id);
    void deleteById(long id);
    void updateById(long id, T obj);
}