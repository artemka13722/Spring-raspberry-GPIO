package com.raspberry.raspberry.services;

import com.raspberry.raspberry.model.GPIOSettings;
import com.raspberry.raspberry.repository.GPIOSettingsRepository;
import com.raspberry.raspberry.services.dht.DHTService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionService {

    private final GPIOSettingsRepository gpioSettingsRepository;

    private final RelayService relayService;

    private final DHTService dhtService;

    private final MotionSensorService motionSensorService;

    @Scheduled(fixedRate = 1000)
    public void action() {
        List<GPIOSettings> allSettings = gpioSettingsRepository.findAll();
        if(allSettings.isEmpty()) {
            return;
        }
        for (GPIOSettings setting : allSettings) {
            switch (setting.getSensor()) {
                case RELAY:
                    relayService.action(setting);
                    break;
                case DHT11:
                    dhtService.action(setting);
                    break;
                case MOTION_SENSOR:
                    motionSensorService.action(setting);
                    break;
                default:
                    break;
            }
        }
    }

}
