package org.bohdan.rest.sensorapi.services;

import org.bohdan.rest.sensorapi.dto.MeasurementDTO;
import org.bohdan.rest.sensorapi.dto.MeasurementDTOWithTime;
import org.bohdan.rest.sensorapi.dto.MeasurementListDTO;
import org.bohdan.rest.sensorapi.exceptions.MeasurementNotAddedException;
import org.bohdan.rest.sensorapi.models.Measurement;
import org.bohdan.rest.sensorapi.models.Sensor;
import org.bohdan.rest.sensorapi.repositories.MeasurementRepository;
import org.bohdan.rest.sensorapi.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        Sensor sensor = measurement.getSensor();
        Optional<Sensor> sensorByName = sensorRepository.getSensorByName(sensor.getName());

        if (sensorByName.isPresent()) {
            long daysAgo = (long) (Math.random() * 10);
            LocalDate currentDate = LocalDate.now();
            LocalDate randomDate = currentDate.minusDays(daysAgo);
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date date = Date.from(randomDate.atStartOfDay(defaultZoneId).toInstant());
            measurement.setMeasurementTime(date);

            measurement.setSensor(sensorByName.get());
            measurementRepository.save(measurement);
            sensorByName.get().getMeasurements().add(measurement);
        }
    }

    public List<Measurement> getMeasurements() {
        return measurementRepository.findAllMeasurements();
    }

    public Long getRainyDaysCount() {
        List<Measurement> allMeasurements = measurementRepository.findAllMeasurements();

        return allMeasurements.stream().filter(Measurement::getRaining).count();
    }

    public MeasurementListDTO convertToMeasurementListDto(List<MeasurementDTOWithTime> measurements) {
        MeasurementListDTO measurementListDTO = new MeasurementListDTO();
        measurementListDTO.setMeasurements(measurements);
        return measurementListDTO;
    }
}
