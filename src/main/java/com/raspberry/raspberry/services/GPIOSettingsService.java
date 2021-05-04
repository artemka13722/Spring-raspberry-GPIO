package com.raspberry.raspberry.services;

import com.raspberry.raspberry.dto.RelayScenatiesDto;
import com.raspberry.raspberry.dto.SetSettingsDto;
import com.raspberry.raspberry.exception.GPIOException;
import com.raspberry.raspberry.model.*;
import com.raspberry.raspberry.model.scripts.Scripts;
import com.raspberry.raspberry.repository.DHTValueRepository;
import com.raspberry.raspberry.repository.GPIOSettingsRepository;
import com.raspberry.raspberry.repository.MotionSensorRepository;
import com.raspberry.raspberry.utils.dtoTransform.GPIOSettingsDtoTransformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.raspberry.raspberry.model.scripts.Scripts.*;

@Service
@RequiredArgsConstructor
public class GPIOSettingsService {

    private final GPIOSettingsRepository gpioSettingsRepository;

    private final GPIOSettingsDtoTransformService gpioSettingsDtoTransformService;

    private final DHTValueRepository dhtValueRepository;

    private final MotionSensorRepository motionSensorRepository;

    public List<Pins> getAllPins() {
        return Arrays.asList(Pins.values());
    }

    public List<Sensor> getAllSensor() {
        return Arrays.asList(Sensor.values());
    }

    // TODO: 30.04.2021 подумать над тем перезаписывать порт или кидать ошибку что он уже используется 
    @Transactional
    public void setupGpio(SetSettingsDto sensor) {
        GPIOSettings gpioSettings = gpioSettingsRepository.findById(sensor.getPin()).orElse(null);
        if(gpioSettings == null) {
            gpioSettings = new GPIOSettings(sensor);
        }
        setupScriptGpio(sensor, gpioSettings);
        gpioSettingsRepository.save(gpioSettings);
    }

    // TODO: 30.04.2021 придумать как проверять сценарии более лучшим способом
    private void setupScriptGpio(SetSettingsDto sensor, GPIOSettings gpioSettings) {
        switch (sensor.getSensor()) {
            case DHT11:
                if(sensor.getScripts().equals(GET_TEMP)){
                    DHTValue dhtValue = dhtValueRepository.findByPin(sensor.getPin());
                    if(dhtValue == null) {
                        dhtValue = new DHTValue();
                        dhtValue.setPin(sensor.getPin());
                    }
                    dhtValue.setStarted(false);
                    dhtValueRepository.save(dhtValue);
                    break;
                } else {
                    throw new GPIOException("Error scripts");
                }
            case RELAY:
                if(sensor.getScripts().equals(ON) || sensor.getScripts().equals(OFF)) {
                    gpioSettings.setNewSettings(sensor);
                    break;
                } else {
                    throw new GPIOException("Error scripts");
                }
            case MOTION_SENSOR:
                if(sensor.getScripts().equals(MOTION_SENSOR_ON)) {
                    MotionSensor motionSensor = motionSensorRepository.findByPin(sensor.getPin());
                    if(motionSensor == null) {
                        motionSensor = new MotionSensor();
                        motionSensor.setPin(sensor.getPin());
                    }
                    motionSensor.setRelayPin(sensor.getRelayMotionPin());
                    if(sensor.getTimeDelay() != null) {
                        motionSensor.setTimeDelay(sensor.getTimeDelay());
                    }
                    motionSensorRepository.save(motionSensor);
                } else {
                    throw new GPIOException("Error scripts");
                }
            default:
                break;
        }
    }

    @Transactional
    public void deleteGpio(Pins pin) {
        GPIOSettings gpioSettings = gpioSettingsRepository.findById(pin).orElseThrow(() -> new GPIOException("Pin not found"));

        switch (gpioSettings.getSensor()) {
            case MOTION_SENSOR:
                motionSensorRepository.delete(motionSensorRepository.findByPin(pin));
                break;
            case DHT11:
                dhtValueRepository.delete(dhtValueRepository.findByPin(pin));
                break;
            default:
                break;
        }
        gpioSettingsRepository.delete(gpioSettings);
    }

    public List<SetSettingsDto> getAllSettings() {
        return gpioSettingsRepository.findAll().stream()
                .map(gpioSettingsDtoTransformService::getSetSettingsDto)
                .collect(Collectors.toList());
    }

    public List<RelayScenatiesDto> getAllScenaties() {
        List<RelayScenatiesDto> relayScenatiesDtos = new ArrayList<>();
        for (Scripts scenaries : Scripts.values()) {
            relayScenatiesDtos.add(new RelayScenatiesDto(scenaries, scenaries.getName(), scenaries.getSensor()));
        }
        return relayScenatiesDtos;
    }
}
