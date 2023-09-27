package org.bohdan.hibernatecore.repository.interfaces;

import java.util.List;
import java.util.function.Function;

public interface CommonDAO {
    <T> T getEntityById(Class<T> entity, Integer id);

    <T, R> List<R> getListOfEntitiesByEntityId(Class<T> entity, Integer id);

    <T, R> R getOwnerByHisEntity(Class<T> entity, Integer id);

    <T> void addEntityForTheOwner(Class<T> entity, Integer entityId);

    void addNewDirectorAndNewMovie();

    <T> void changeDirectorInExistingFilm(Class<T> entity, Integer entityId, Integer directorId);

    <T> void removeDirectorsFilm(Class<T> entity, Integer entityId, Integer movieId);
}
