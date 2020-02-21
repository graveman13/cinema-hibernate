package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.MovieSession;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Autowired
    private SessionFactory sessionFactory;
    private static final Logger LOGGER = Logger.getLogger(MovieSessionDaoImpl.class);

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM MovieSession where movie.id =: movieId "
                    + "and year(showTime) =: year"
                    + " and month(showTime) =: month and day(showTime) =: day");
            query.setParameter("movieId", movieId);
            query.setParameter("year", date.getYear());
            query.setParameter("month", date.getMonthValue());
            query.setParameter("day", date.getDayOfMonth());
            List<MovieSession> list = query.list();
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Can't find session");
        }
    }

    @Override
    public MovieSession getById(Long id) {
        try {
            return sessionFactory.openSession().get(MovieSession.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get MovieSession", e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(movieSession);
            movieSession.setId(id);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.info(e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert Movie entity", e);
        }
        return movieSession;
    }
}
