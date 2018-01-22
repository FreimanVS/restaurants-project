package com.freimanvs.restaurants.dao;

import com.freimanvs.restaurants.entity.User;
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
public class UserDAO implements DAO<User> {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public long add(User obj) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(obj);
        return obj.getId();
    }

    @Override
    public List<User> getList() {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User getById(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.byId(User.class).load(id);
        session.delete(user);
    }

    @Override
    public void updateById(long id, User obj) {
        Session session = sessionFactory.getCurrentSession();
        User userFromDB = session.byId(User.class).load(id);

        if (obj.getUsername() != null) {
            userFromDB.setUsername(obj.getUsername());
        }

        if(obj.getPassword() != null) {
            userFromDB.setPassword(obj.getPassword());
        }

        if(obj.getLast_vote() != null) {
            userFromDB.setLast_vote(obj.getLast_vote());
        }

        if(obj.getRest() != null) {
            userFromDB.setRest(obj.getRest());
        }

        session.flush();
    }
}