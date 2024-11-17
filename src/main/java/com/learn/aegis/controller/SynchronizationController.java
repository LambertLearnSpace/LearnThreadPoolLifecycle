package com.learn.aegis.controller;

import com.learn.aegis.service.SynchronizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/sync")
public class SynchronizationController {

    @Autowired
    private SynchronizationService synchronizationService;

    @GetMapping("/increase")
    public int increaseCount() {
        return synchronizationService.increaseCount();
    }

    @GetMapping("/increaseWithLock")
    public int increaseCountWithLock() {
        return synchronizationService.increaseCountWithLock();
    }
} 