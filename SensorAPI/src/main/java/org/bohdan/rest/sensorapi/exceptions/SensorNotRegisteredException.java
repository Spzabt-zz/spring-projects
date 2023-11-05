package org.bohdan.rest.sensorapi.exceptions;

public class SensorNotRegisteredException extends RuntimeException {
    public SensorNotRegisteredException(String msg) {
        super(msg);
    }
}
