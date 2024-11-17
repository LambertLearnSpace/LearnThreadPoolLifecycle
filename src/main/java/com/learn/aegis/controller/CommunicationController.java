package com.learn.aegis.controller;

import com.learn.aegis.service.CommunicationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/communication")
public class CommunicationController {

    @Autowired
    private CommunicationService communicationService;

    @GetMapping("/produce")
    public String produce() throws InterruptedException {
        return communicationService.produce();
    }

    @GetMapping("/consume")
    public String consume() throws InterruptedException {
        return communicationService.consume();
    }
} 