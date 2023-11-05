package org.bohdan.rest.sensorapi.repositories;

import org.bohdan.rest.sensorapi.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    @Query("SELECT m FROM Measurement m JOIN FETCH m.sensor")
    List<Measurement> findAllMeasurements();
}
