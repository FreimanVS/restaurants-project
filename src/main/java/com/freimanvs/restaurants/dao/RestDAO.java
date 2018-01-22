package com.freimanvs.restaurants.dao;

import com.freimanvs.restaurants.entity.Restaurant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Brightsunrise
 * @version 1.0
 */
@Repository
public class RestDAO implements DAO<Restaurant> {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public long add(Restaurant obj) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(obj);
        return obj.getId();
    }

    @Override
    public Restaurant getById(long id) {
        return sessionFactory.getCurrentSession().get(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> getList() {
        Session session = sessionFactory.getCurrentSession();
        Query<Restaurant> query = session.createQuery("from Restaurant", Restaurant.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Restaurant rest = session.byId(Restaurant.class).load(id);
        session.delete(rest);
    }

    @Override
    public void updateById(long id, Restaurant obj) {
        Session session = sessionFactory.getCurrentSession();
        Restaurant restFromDB = session.byId(Restaurant.class).load(id);

        if (obj.getMenu() != null) {
            restFromDB.setMenu(obj.getMenu());
        }

        if (obj.getName() != null) {
            restFromDB.setName(obj.getName());
        }
        session.flush();
    }
}