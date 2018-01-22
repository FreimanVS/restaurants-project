package com.freimanvs.restaurants.dao;

import com.freimanvs.restaurants.entity.Menu;
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
public class MenuDAO implements DAO<Menu> {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public long add(Menu obj) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(obj);
        return obj.getId();
    }

    @Override
    public List<Menu> getList() {
        Session session = sessionFactory.getCurrentSession();
        Query<Menu> query = session.createQuery("from Menu", Menu.class);
        return query.getResultList();
    }

    @Override
    public Menu getById(long id) {
        return sessionFactory.getCurrentSession().get(Menu.class, id);
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Menu rest = session.byId(Menu.class).load(id);
        session.delete(rest);
    }

    @Override
    public void updateById(long id, Menu obj) {
        Session session = sessionFactory.getCurrentSession();
        Menu menuFromDB = session.byId(Menu.class).load(id);
        if (obj.getDish() != null) {
            menuFromDB.setDish(obj.getDish());
        }

        if (obj.getPrice() > 0.0) {
            menuFromDB.setPrice(obj.getPrice());
        }
        session.flush();
    }
}