package org.bohdan.rest.sensorapi.validator;

import org.bohdan.rest.sensorapi.models.Measurement;
import org.bohdan.rest.sensorapi.models.Sensor;
import org.bohdan.rest.sensorapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Measurement.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        Optional<Sensor> sensorByName = sensorService.getSensorByName(measurement.getSensor().getName());
        if (sensorByName.isEmpty()) {
            errors.rejectValue("sensor", "", "Sensors with such name does not found in the system");
        }
    }
}
