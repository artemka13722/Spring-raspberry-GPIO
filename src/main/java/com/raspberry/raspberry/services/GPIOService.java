package com.raspberry.raspberry.services;

import com.pi4j.io.gpio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class GPIOService {

    public void gpioSetPinLow(Pin raspberryPin) {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(raspberryPin, PinState.LOW);
        gpio.shutdown();
        gpio.unprovisionPin(pin);
        log.info(String.format("relay on pin %s on", raspberryPin.getName()));
    }

    public void gpioSetPinHIGH(Pin raspberryPin) {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(raspberryPin, PinState.HIGH);
        gpio.shutdown();
        gpio.unprovisionPin(pin);
        log.info(String.format("relay on pin %s off", raspberryPin.getName()));
    }

    public boolean getPinState(Pin raspberryPin) {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput pin = gpio.provisionDigitalInputPin(raspberryPin);
        boolean state = pin.getState() == PinState.HIGH;
        gpio.shutdown();
        gpio.unprovisionPin(pin);
        return state;
    }

}
