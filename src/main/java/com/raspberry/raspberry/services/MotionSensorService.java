package com.raspberry.raspberry.services;

import com.pi4j.io.gpio.Pin;
import com.raspberry.raspberry.model.GPIOSettings;
import com.raspberry.raspberry.model.MotionSensor;
import com.raspberry.raspberry.repository.MotionSensorRepository;
import com.raspberry.raspberry.utils.RaspiPinUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class MotionSensorService {

    private final GPIOService gpioService;

    private final MotionSensorRepository motionSensorRepository;

    @Transactional
    public void action(GPIOSettings gpioSettings) {
        MotionSensor motionSensor = motionSensorRepository.findByPin(gpioSettings.getPin());
        Pin pin = RaspiPinUtil.getPin(gpioSettings.getPin());
        boolean motionState = gpioService.getPinState(pin);

        if(motionState) {
            motionSensor.setLastUpdate(new Date(Clock.systemUTC().millis()));
            gpioService.gpioSetPinLow(RaspiPinUtil.getPin(motionSensor.getRelayPin()));
            motionSensorRepository.save(motionSensor);
        } else {
            if(motionSensor.getLastUpdate() != null) {
                Date checkDate = new Date(Clock.systemUTC().millis());
                boolean lastUpdate = checkDate.getTime() - motionSensor.getLastUpdate().getTime() > motionSensor.getTimeDelay();
                if(lastUpdate) {
                    gpioService.gpioSetPinHIGH(RaspiPinUtil.getPin(motionSensor.getRelayPin()));
                }
            } else {
                gpioService.gpioSetPinHIGH(RaspiPinUtil.getPin(motionSensor.getRelayPin()));
            }
        }
    }
}
