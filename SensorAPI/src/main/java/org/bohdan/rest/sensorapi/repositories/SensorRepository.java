package org.bohdan.rest.sensorapi.repositories;

import org.bohdan.rest.sensorapi.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> getSensorByName(String name);
}
