package com.raspberry.raspberry.controllers;

import com.raspberry.raspberry.services.RelayService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/relay-controller")
@RestController
@RequiredArgsConstructor
public class RelayController {
    private final RelayService relayService;


}
