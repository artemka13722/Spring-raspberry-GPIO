package com.raspberry.raspberry.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Clock;
import java.util.Date;

@Entity
@Data
public class DHTValue {
    @Id
    @Enumerated(EnumType.STRING)
    private Pins pin;

    private double temperature;

    private double humidity;

    private boolean started;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate = new Date(Clock.systemUTC().millis());
}
