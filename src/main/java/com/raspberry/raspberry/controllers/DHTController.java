package com.raspberry.raspberry.controllers;

import com.raspberry.raspberry.DHT11.DhtData;
import com.raspberry.raspberry.dto.PinDto;
import com.raspberry.raspberry.services.dht.DHTService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dht-controller")
@Api("Контроллер управления датчиком температуры и влажности")
@RequiredArgsConstructor
public class DHTController {

    private final DHTService dhtService;

    @PostMapping("/get-value")
    @ApiOperation("Получение значений температуры и влажности")
    public DhtData getValue(@RequestBody PinDto pin) {
        return dhtService.getData(pin.getPin());
    }
}
