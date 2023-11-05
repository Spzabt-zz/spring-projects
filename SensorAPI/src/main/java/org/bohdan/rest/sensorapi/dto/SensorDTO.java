package org.bohdan.rest.sensorapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {
    @NotBlank(message = "Please provide sensor name")
    @Size(min = 3, max = 30, message = "Name must be in range from 3 to 30 characters")
    private String name;
}
