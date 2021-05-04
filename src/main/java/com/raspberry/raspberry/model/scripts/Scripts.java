package com.raspberry.raspberry.model.scripts;

import com.raspberry.raspberry.model.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Scripts {
    ON("Включить рэле", Sensor.RELAY),
    OFF("Выключить рэле", Sensor.RELAY),
    GET_TEMP("Получить температуру и влажность", Sensor.DHT11),
    MOTION_SENSOR_ON("Активация рэле при движении", Sensor.MOTION_SENSOR);

    private final String name;
    private final Sensor sensor;
}
