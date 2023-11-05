package org.bohdan.sensorClient.dto;

public class Sensor {
    private String name;

    public Sensor(String name) {
        this.name = name;
    }

    public Sensor() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                '}';
    }
}
