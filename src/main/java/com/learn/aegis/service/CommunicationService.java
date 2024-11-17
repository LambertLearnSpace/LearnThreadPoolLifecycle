package com.learn.aegis.service;

public interface CommunicationService {
    String produce() throws InterruptedException;
    String consume() throws InterruptedException;
} 