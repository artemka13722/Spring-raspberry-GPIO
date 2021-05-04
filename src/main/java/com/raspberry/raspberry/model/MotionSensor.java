package com.raspberry.raspberry.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Clock;
import java.util.Date;

@Entity
@Data
public class MotionSensor {
    @Id
    @Enumerated(EnumType.STRING)
    private Pins pin;

    private Pins relayPin;

    private Integer timeDelay;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
}
