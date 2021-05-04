package com.raspberry.raspberry.model;

import com.raspberry.raspberry.dto.SetSettingsDto;
import com.raspberry.raspberry.model.scripts.Scripts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GPIOSettings {
    @Id
    @Enumerated(EnumType.STRING)
    private Pins pin;

    @Enumerated(EnumType.STRING)
    private Sensor sensor;

    @Enumerated(EnumType.STRING)
    private Scripts scripts;

    public GPIOSettings(SetSettingsDto sensor) {
        this.pin = sensor.getPin();
        this.sensor = sensor.getSensor();
        this.scripts = sensor.getScripts();
    }

    public void setNewSettings(SetSettingsDto sensor) {
        this.pin = sensor.getPin();
        this.sensor = sensor.getSensor();
        this.scripts = sensor.getScripts();
    }
}
