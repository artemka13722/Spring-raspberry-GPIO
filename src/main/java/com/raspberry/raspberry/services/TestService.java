package com.raspberry.raspberry.services;

import com.pi4j.io.gpio.*;
import com.raspberry.raspberry.DHT11.DHT11;
import com.raspberry.raspberry.DHT11.DHTxx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TestService {

    private static boolean onLed = false;

    public void setOff() {
        onLed = false;
    }

    public void setOn() {
        onLed = true;
    }

    public void onLed() {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "PinLED", PinState.HIGH);
        gpio.shutdown();
        gpio.unprovisionPin(pin);
        log.info("GPIO_01 HIGH");
    }

    public void offLed() {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.LOW);
        gpio.shutdown();
        gpio.unprovisionPin(pin);
        log.info("GPIO_01 LOW");
    }

    public void input() throws InterruptedException {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput pin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);

        while (onLed) {
            if (pin.getState() == PinState.HIGH) {
                onLed();
            } else {
                offLed();
            }
            Thread.sleep(500);
        }
        gpio.shutdown();
        gpio.unprovisionPin(pin);
    }

    @Scheduled(fixedRate = 1000)
    public void test() throws InterruptedException {
        if(onLed) {
            input();
        }
    }

    public void dht11() {
        DHTxx dht11 = new DHT11(RaspiPin.GPIO_01);
        try {
            dht11.init();
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println(dht11.getData());
                    Thread.sleep(2000);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
