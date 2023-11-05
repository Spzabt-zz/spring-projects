package org.bohdan.sensorClient.response;

import org.bohdan.sensorClient.dto.Measurement;

import java.util.List;

public class AllMeasurementsResponse {
    private List<Measurement> measurements;

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }
}
