package com.raspberry.raspberry.services.dht;

import com.raspberry.raspberry.DHT11.DhtData;
import com.raspberry.raspberry.exception.GPIOException;
import com.raspberry.raspberry.model.DHTValue;
import com.raspberry.raspberry.model.GPIOSettings;
import com.raspberry.raspberry.model.Pins;
import com.raspberry.raspberry.repository.DHTValueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class DHTService {

    private final DHTValueRepository dhtValueRepository;

    @Transactional
    public void action(GPIOSettings gpioSettings) {
        DHTValue dhtValue = dhtValueRepository.findByPin(gpioSettings.getPin());
        Date checkDate = new Date(Clock.systemUTC().millis());
        boolean lastUpdate = checkDate.getTime() - dhtValue.getLastUpdate().getTime() > 300000;

        if (!dhtValue.isStarted() || lastUpdate) {
            dhtValue.setStarted(true);
            dhtValueRepository.save(dhtValue);
            Thread thread = new DHTThread(gpioSettings, dhtValueRepository);
            thread.start();
            log.info("DHT add action");
        }
    }

    public DhtData getData(Pins pin) {
        DHTValue dhtValue = dhtValueRepository.findById(pin).orElseThrow(() -> new GPIOException("Pin not found"));
        return new DhtData(dhtValue.getTemperature(), dhtValue.getHumidity());
    }
}
