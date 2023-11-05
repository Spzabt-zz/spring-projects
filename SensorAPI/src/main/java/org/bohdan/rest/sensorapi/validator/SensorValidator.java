package org.bohdan.rest.sensorapi.validator;

import org.bohdan.rest.sensorapi.models.Sensor;
import org.bohdan.rest.sensorapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorRepository) {
        this.sensorService = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        Optional<Sensor> sensorByName = sensorService.getSensorByName(sensor.getName());
        if (sensorByName.isPresent()) {
            errors.rejectValue("name", "", "Sensor with such name already in the system");
        }
    }
}
