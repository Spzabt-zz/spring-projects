package org.bohdan.oneToMany.repository;

import org.hibernate.SessionFactory;

public class DirectorRepository extends CommonDaoRepoImpl {
    public DirectorRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public <T> T getEntityById(Class<T> entity, Integer id) {
        return super.getEntityById(entity, id);
    }

    @Override
    public <T, R> R getOwnerByHisEntity(Class<T> entity, Integer id) {
        return super.getOwnerByHisEntity(entity, id);
    }

    @Override
    public void addNewDirectorAndNewMovie() {
        super.addNewDirectorAndNewMovie();
    }

    @Override
    public <T> void changeDirectorInExistingFilm(Class<T> entity, Integer entityId, Integer directorId) {
        super.changeDirectorInExistingFilm(entity, entityId, directorId);
    }
}
