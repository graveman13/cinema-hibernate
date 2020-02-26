package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.model.User;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User add(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long itemId = (Long) session.save(user);
            transaction.commit();
            user.setId(itemId);
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add user", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            root.fetch("roles", JoinType.LEFT);
            cq.select(root).where(cb.equal(root.get("email"), email));
            return session.createQuery(cq).uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't find user by email " + email, e);
        }
    }

    @Override
    public User getById(Long id) {
        try {
            return sessionFactory.openSession().get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get User", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User ", User.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users", e);
        }
    }
}
