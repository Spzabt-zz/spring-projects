package org.bohdan.hibernatecore.repository.interfaces;

public interface CommonDAO {
    <T> T getEntity(Class<T> entity);
}
