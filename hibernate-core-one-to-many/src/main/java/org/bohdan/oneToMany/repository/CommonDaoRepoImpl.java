package org.bohdan.oneToMany.repository;

import org.bohdan.oneToMany.model.Director;
import org.bohdan.oneToMany.model.Movie;
import org.bohdan.oneToMany.repository.interfaces.CommonDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonDaoRepoImpl implements CommonDAO {
    private final SessionFactory sessionFactory;

    public CommonDaoRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> T getEntityById(Class<T> entity, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        T entityById = session.get(entity, id);

        session.getTransaction().commit();

        return entityById;
    }

    @Override
    public <T, R> List<R> getListOfEntitiesByEntityId(Class<T> entity, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        T entityById = session.get(entity, id);

        List<R> resultList = new ArrayList<>();

        if (entityById != null)
            if (entityById instanceof Director director)
                resultList.addAll((List<R>) director.getMovies());

        session.getTransaction().commit();

        return resultList;
    }

    @Override
    public <T, R> R getOwnerByHisEntity(Class<T> entity, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        T entityById = session.get(entity, id);
        R owner = null;

        if (entityById != null)
            if (entityById instanceof Movie movie)
                owner = (R) movie.getDirector();

        session.getTransaction().commit();

        return owner;
    }

    @Override
    public <T> void addEntityForTheOwner(Class<T> entity, Integer entityId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Movie film = new Movie("SomeMovie", 1999);
        T entityById = session.get(entity, entityId);
        if (entityById != null)
            if (entityById instanceof Director director) {
                film.setDirector(director);

                director.setMovies(new ArrayList<>(Collections.singletonList(film)));

                session.persist(film);
            }

        session.getTransaction().commit();
    }

    @Override
    public void addNewDirectorAndNewMovie() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Director director = new Director("New Name", 45);
        Movie movie = new Movie("New Movie", 2023);

        movie.setDirector(director);

        director.setMovies(new ArrayList<>(Collections.singletonList(movie)));

        session.persist(director);
        session.persist(movie);

        session.getTransaction().commit();
    }

    @Override
    public <T> void changeDirectorInExistingFilm(Class<T> entity, Integer entityId, Integer directorId) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        //2
        Director director = session.get(Director.class, directorId);
        //1
        T entityById = session.get(entity, entityId);

        if (entityById != null)
            if (entityById instanceof Movie movie) {
                movie.getDirector().getMovies().remove(movie);

                movie.setDirector(director);

                director.getMovies().add(movie);
            }

        session.getTransaction().commit();
    }

    @Override
    public <T> void removeDirectorsFilm(Class<T> entity, Integer entityId, Integer movieId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        T entityById = session.get(entity, entityId);

        if (entityById != null)
            if (entityById instanceof Director director) {
                List<Movie> movies = director.getMovies();

                Movie movie = movies.stream().filter(m -> m.getId() == movieId).findAny().orElse(null);

                session.remove(movie);

                director.getMovies().remove(movie);
            }

        session.getTransaction().commit();
    }
}
