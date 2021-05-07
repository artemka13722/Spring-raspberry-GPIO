package com.raspberry.raspberry.controllers;

import com.raspberry.raspberry.services.bluetooth.BluetoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/key-controller")
@RestController
@RequiredArgsConstructor
public class KeyController {

    private final BluetoothService bluetoothService;


    @GetMapping("/get-key")
    public String getKey() {
        return bluetoothService.generateEncodedToken("192.168.1.132:8080");
    }
}
