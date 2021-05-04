package com.raspberry.raspberry.services;

import com.pi4j.io.gpio.Pin;
import com.raspberry.raspberry.model.GPIOSettings;
import com.raspberry.raspberry.utils.RaspiPinUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RelayService {

    private final GPIOService gpioService;

    public void action(GPIOSettings gpioSettings) {
        Pin pin = RaspiPinUtil.getPin(gpioSettings.getPin());
        switch (gpioSettings.getScripts()) {
            case ON:
                gpioService.gpioSetPinLow(pin);
                break;
            case OFF:
                gpioService.gpioSetPinHIGH(pin);
                break;
        }
    }
}
