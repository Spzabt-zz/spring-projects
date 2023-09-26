package org.bohdan.hibernatecore.repository;

import org.bohdan.hibernatecore.model.Director;
import org.bohdan.hibernatecore.repository.interfaces.CommonDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class DirectorRepository implements CommonDAO {
    private final SessionFactory sessionFactory;

    public DirectorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> T getEntityById(Class<T> entity, Integer id) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        T director = session.get(entity, id);

        session.getTransaction().commit();

        return director;
    }

    @Override
    public <T, R> List<R> getListOfEntitiesByEntityId(Class<T> entityClass, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        T entityById = session.get(entityClass, id);

        List<R> resultList = new ArrayList<>();

        if (entityById != null) {
            if (entityById instanceof Director director) {
                List<R> movies = (List<R>) director.getMovies();
                resultList.addAll(movies);
            }
        }

        session.getTransaction().commit();

        return resultList;
    }
}
