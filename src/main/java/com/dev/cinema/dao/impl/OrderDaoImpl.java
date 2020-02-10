package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;
import com.dev.cinema.util.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Long itemId = (Long) session.save(order);
            transaction.commit();
            order.setId(itemId);
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert order entity", e);
        }
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery(
                    "from Order where user.id =:userId", Order.class);
            query.setParameter("userId", user.getId());
            return query.list();
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving all Movies", e);
        }
    }
}
