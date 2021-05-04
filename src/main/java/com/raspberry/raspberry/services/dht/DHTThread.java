package com.raspberry.raspberry.services.dht;

import com.pi4j.io.gpio.Pin;
import com.raspberry.raspberry.DHT11.DHT11;
import com.raspberry.raspberry.DHT11.DHTxx;
import com.raspberry.raspberry.DHT11.DhtData;
import com.raspberry.raspberry.model.DHTValue;
import com.raspberry.raspberry.model.GPIOSettings;
import com.raspberry.raspberry.repository.DHTValueRepository;
import com.raspberry.raspberry.utils.RaspiPinUtil;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
public class DHTThread extends Thread{

    private final GPIOSettings gpioSettings;

    private final DHTValueRepository dhtValueRepository;

    public DHTThread(GPIOSettings gpioSettings, DHTValueRepository dhtValueRepository) {
        this.gpioSettings = gpioSettings;
        this.dhtValueRepository = dhtValueRepository;
    }

    @Override
    @Transactional
    public void run() {
        DHTValue dhtValue;
        do {
            dhtValue = dhtValueRepository.findById(gpioSettings.getPin()).orElseGet(null);
            if(dhtValue == null) {
                log.info("DTH thread death");
                break;
            }
            Pin pin = RaspiPinUtil.getPin(gpioSettings.getPin());
            DHTxx dht11 = new DHT11(pin);
            try {
                dht11.init();
                while (true) {
                    try {
                        DhtData dhtData = dht11.getData();
                        Thread.sleep(2000);
                        dhtValue.setHumidity(dhtData.getHumidity());
                        dhtValue.setTemperature(dhtData.getTemperature());
                        dhtValueRepository.save(dhtValue);
                        log.info("Update Temp");
                        break;
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } while (dhtValue.isStarted());
    }
}
