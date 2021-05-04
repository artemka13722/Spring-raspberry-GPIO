package com.raspberry.raspberry.dto;

import com.raspberry.raspberry.model.Pins;
import com.raspberry.raspberry.model.Sensor;
import com.raspberry.raspberry.model.scripts.Scripts;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class SetSettingsDto {
    private Pins pin;

    private Sensor sensor;

    private Scripts scripts;

    private Pins relayMotionPin;

    private Integer timeDelay;
}
