package com.raspberry.raspberry.services.bluetooth;

import com.raspberry.raspberry.services.dht.DHTThread;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Base64;
import java.util.Enumeration;

@Service
@RequiredArgsConstructor
@Slf4j
public class BluetoothService {

    private static final int MAX_TOKEN_LENGTH = 64;

    private final StringEncryptor stringEncryptor;

    @Value("${secret.bluetooth.key}")
    private String bluetoothKey;

    @PostConstruct
    public void startBluetooth() {
        String key = generateEncodedToken("192.168.1.32:8080");
        Thread thread = new BluetoothThread(bluetoothKey, key);
        thread.start();
        log.info("Bluetooth start");
    }

    public void getIp() throws SocketException {
        for (Enumeration<NetworkInterface> ifaces =
             NetworkInterface.getNetworkInterfaces();
             ifaces.hasMoreElements(); ) {
            NetworkInterface iface = ifaces.nextElement();
            System.out.println(iface.getName() + ":");
            for (Enumeration<InetAddress> addresses =
                 iface.getInetAddresses();
                 addresses.hasMoreElements(); ) {
                InetAddress address = addresses.nextElement();
                System.out.println("  " + address);
            }
        }
    }

    public String generateEncodedToken(String address) {
        String encodedToken = stringEncryptor.encrypt(address);
        String b64UrlToken = Base64.getUrlEncoder().encodeToString(encodedToken.getBytes());
        if (b64UrlToken.length() > MAX_TOKEN_LENGTH) {
            log.error("Encoded token length for telegram is more than {}", MAX_TOKEN_LENGTH);
            throw new RuntimeException("Failed to create telegram bot link");
        }
        return b64UrlToken;
    }

}
