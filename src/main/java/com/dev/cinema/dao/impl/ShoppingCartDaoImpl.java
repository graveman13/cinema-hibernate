package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ShoppingCart getById(Long id) {
        try {
            return sessionFactory.openSession().get(ShoppingCart.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get MovieSession", e);
        }
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(shoppingCart);
            transaction.commit();
            shoppingCart.setId(id);
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert ShoppingCart entity", e);
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query queryGetShoppingCart = session.createQuery(
                    "from ShoppingCart  where user.id =:userId ");
            queryGetShoppingCart.setParameter("userId", user.getId());
            ShoppingCart sc = (ShoppingCart) queryGetShoppingCart.uniqueResult();

            List<Ticket> tickets = session.createQuery("select t from ShoppingCart as sc "
                    + "join sc.tickets as t where sc.user.id =:userId", Ticket.class)
                    .setParameter("userId", user.getId()).list();

            sc.setTickets(tickets);
            return sc;
        } catch (Exception e) {
            throw new RuntimeException("Can't get shoppinging cart ", e);
        }
    }

    @Override
    public ShoppingCart getByUserId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query queryGetShoppingCart = session.createQuery(
                    "from ShoppingCart  where user.id =:id ");
            queryGetShoppingCart.setParameter("id", id);
            ShoppingCart sc = (ShoppingCart) queryGetShoppingCart.uniqueResult();

            List<Ticket> tickets = session.createQuery("select t from ShoppingCart as sc "
                    + "join sc.tickets as t where sc.user.id =:id", Ticket.class)
                    .setParameter("id", id).list();

            sc.setTickets(tickets);
            return sc;
        } catch (Exception e) {
            throw new RuntimeException("Can't get shoppinging cart ", e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Movie entity", e);
        }
    }
}
