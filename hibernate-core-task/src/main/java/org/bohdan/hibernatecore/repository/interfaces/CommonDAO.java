package org.bohdan.hibernatecore.repository.interfaces;

import java.util.List;
import java.util.function.Function;

public interface CommonDAO {
    <T> T getEntityById(Class<T> entity, Integer id);

    <T, R> List<R> getListOfEntitiesByEntityId(Class<T> entity, Integer id);
}
