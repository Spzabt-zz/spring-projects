package org.bohdan.hibernatecore.repository;

import org.bohdan.hibernatecore.repository.interfaces.CommonDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DirectorRepository implements CommonDAO {
    private final SessionFactory sessionFactory;

    public DirectorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> T getEntity(Class<T> entity) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        T t = session.get(entity, 2);

        session.getTransaction().commit();

        return t;
    }
}
