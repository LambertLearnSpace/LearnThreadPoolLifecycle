package com.learn.aegis.service.impl;

import com.learn.aegis.service.CommunicationService;
import org.springframework.stereotype.Service;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommunicationServiceImpl implements CommunicationService {

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    @Override
    public String produce() throws InterruptedException {
        queue.put("item");
        log.info("Produced an item.");
        return "Produced an item";
    }

    @Override
    public String consume() throws InterruptedException {
        String item = queue.take();
        log.info("Consumed an item: {}", item);
        return "Consumed an item: " + item;
    }
} 