package org.bohdan.rest.sensorapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Measurement")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Min(value = -100, message = "Can't be less than -100")
    @Max(value = 100, message = "Can't be greater than 100")
    @NotNull(message = "Value can't be empty")
    @Column(name = "value")
    private Float value;

    @NotNull(message = "Raining value can't be empty")
    @Column(name = "raining")
    private Boolean raining;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "measurement_time")
    private Date measurementTime;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @ToString.Exclude private Sensor sensor;

    public Measurement(float value, boolean raining) {
        this.value = value;
        this.raining = raining;
    }
}
