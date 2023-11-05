package org.bohdan.rest.sensorapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MeasurementDTOWithTime {
    @Min(value = -100, message = "Can't be less than -100")
    @Max(value = 100, message = "Can't be greater than 100")
    @NotNull(message = "Value can't be empty")
    private Float value;

    @NotNull(message = "Raining value can't be empty")
    private Boolean raining;

    private SensorDTO sensor;

    private Date measurementTime;
}
