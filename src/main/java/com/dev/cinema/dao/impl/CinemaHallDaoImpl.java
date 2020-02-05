package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.CinemaHallDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.util.HibernateUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import org.hibernate.Transaction;

import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(CinemaHallDaoImpl.class);

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(cinemaHall);
            cinemaHall.setId(id);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return cinemaHall;
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM CinemaHall ");
            List<CinemaHall> list = query.list();
            return list;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("Can't get all cinema hall");
        }
    }
}
