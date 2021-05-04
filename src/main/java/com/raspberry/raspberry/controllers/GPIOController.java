package com.raspberry.raspberry.controllers;

import com.raspberry.raspberry.dto.PinDto;
import com.raspberry.raspberry.dto.RelayScenatiesDto;
import com.raspberry.raspberry.dto.SetSettingsDto;
import com.raspberry.raspberry.model.Pins;
import com.raspberry.raspberry.model.Sensor;
import com.raspberry.raspberry.services.GPIOSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/gpio-controller")
@RestController
@RequiredArgsConstructor
@Slf4j
@Api("Контроллер для управления настройками пинов")
public class GPIOController {

    private final GPIOSettingsService gpioSettingsService;

    @GetMapping("/all-pins")
    @ApiOperation("Получение всех доступных пинов")
    public List<Pins> getAllPins() {
        return gpioSettingsService.getAllPins();
    }

    @GetMapping("/all-sensor")
    @ApiOperation("Получение всех доступных датчиков")
    public List<Sensor> getAllSensor() {
        return gpioSettingsService.getAllSensor();
    }

    @GetMapping("/all-settings")
    @ApiOperation("Получение списка установленных настроек")
    public List<SetSettingsDto> getAllSettings() {
        return gpioSettingsService.getAllSettings();
    }

    @PostMapping("/set-pin")
    @ApiOperation("Настройка пина")
    public void setPinSettings(@RequestBody SetSettingsDto settings) {
        gpioSettingsService.setupGpio(settings);
    }

    @DeleteMapping("/unset-pin")
    @ApiOperation("Удаление настройки пина")
    public void deletePinSettings(@RequestBody PinDto pinDto) {
        gpioSettingsService.deleteGpio(pinDto.getPin());
    }

    @GetMapping("/get-all-scenaries")
    @ApiOperation("Получение списка всех доступных сценариев")
    public List<RelayScenatiesDto> getAllScenaties() {
        return gpioSettingsService.getAllScenaties();
    }
}
