package com.raspberry.raspberry.utils;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.raspberry.raspberry.model.Pins;

public class RaspiPinUtil {

    public static Pin getPin(Pins pins) {
        switch (pins) {
            case GPIO_0:
                return RaspiPin.GPIO_00;
            case GPIO_1:
                return RaspiPin.GPIO_01;
            case GPIO_2:
                return RaspiPin.GPIO_02;
            case GPIO_3:
                return RaspiPin.GPIO_03;
            case GPIO_4:
                return RaspiPin.GPIO_04;
            case GPIO_5:
                return RaspiPin.GPIO_05;
            case GPIO_6:
                return RaspiPin.GPIO_06;
            case GPIO_7:
                return RaspiPin.GPIO_07;
            default:
                return null;
        }
    }

}
