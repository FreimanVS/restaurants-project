package com.freimanvs.restaurants.dao;

import com.freimanvs.restaurants.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Freiman V.S.
 * @version 1.0
 */
@Repository
public class RoleDAO implements DAO<Role> {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public long add(Role obj) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(obj);
        return obj.getId();
    }

    @Override
    public List<Role> getList() {
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery("from Role", Role.class);
        return query.getResultList();
    }

    @Override
    public Role getById(long id) {
        return sessionFactory.getCurrentSession().get(Role.class, id);
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Role role = session.byId(Role.class).load(id);
        session.delete(role);
    }

    @Override
    public void updateById(long id, Role obj) {
        Session session = sessionFactory.getCurrentSession();
        Role roleFromDB = session.byId(Role.class).load(id);

        if (obj.getName() != null) {
            roleFromDB.setName(obj.getName());
        }
        if (obj.getUsers() != null && !obj.getUsers().isEmpty()) {
            roleFromDB.setUsers(obj.getUsers());
        }
        session.flush();
    }
}