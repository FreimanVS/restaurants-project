package com.freimanvs.restaurants.service;

import java.util.List;

/**
 * @author Brightsunrise
 * @version 1.0
 */
public interface TheService<T> {
    long add(T obj);
    List<T> getList();
    T getById(long id);
    void deleteById(long id);
    void updateById(long id, T obj);
}