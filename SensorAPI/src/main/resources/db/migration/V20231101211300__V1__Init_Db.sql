CREATE TABLE if not exists Sensor
(
    id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name varchar(30) UNIQUE                               NOT NULL
);

CREATE TABLE if not exists Measurement
(
    id        int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    value     float(2)                                         NOT NULL,
    raining   boolean                                          NOT NULL,
    sensor_id int REFERENCES Sensor (id) ON DELETE CASCADE
);