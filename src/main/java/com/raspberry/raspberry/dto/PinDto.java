package com.raspberry.raspberry.dto;

import com.raspberry.raspberry.model.Pins;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class PinDto {
    @ApiParam("GPIO pin")
    private Pins pin;
}
