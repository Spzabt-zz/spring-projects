package org.bohdan.hibernatecore.repository;

import org.hibernate.SessionFactory;

public class MovieRepository {
    private final SessionFactory sessionFactory;

    public MovieRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
