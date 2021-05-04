package com.raspberry.raspberry.dto;

import com.raspberry.raspberry.model.Sensor;
import com.raspberry.raspberry.model.scripts.Scripts;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelayScenatiesDto {
    private Scripts scripts;
    private String name;
    private Sensor sensor;
}
