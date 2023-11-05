package org.bohdan.rest.sensorapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeasurementListDTO {
    private List<MeasurementDTOWithTime> measurements;
}
