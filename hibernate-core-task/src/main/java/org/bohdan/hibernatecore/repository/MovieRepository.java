package org.bohdan.hibernatecore.repository;

import org.hibernate.SessionFactory;

import java.util.List;

public class MovieRepository extends CommonDaoRepoImpl {
    public MovieRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public <T> T getEntityById(Class<T> entity, Integer id) {
        return super.getEntityById(entity, id);
    }

    @Override
    public <T, R> List<R> getListOfEntitiesByEntityId(Class<T> entity, Integer id) {
        return super.getListOfEntitiesByEntityId(entity, id);
    }

    @Override
    public <T> void addEntityForTheOwner(Class<T> entity, Integer entityId) {
        super.addEntityForTheOwner(entity, entityId);
    }

    @Override
    public <T> void removeDirectorsFilm(Class<T> entity, Integer entityId, Integer movieId) {
        super.removeDirectorsFilm(entity, entityId, movieId);
    }
}
