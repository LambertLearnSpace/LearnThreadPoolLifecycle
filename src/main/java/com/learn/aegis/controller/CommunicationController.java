package com.learn.aegis.controller;

import com.learn.aegis.service.CommunicationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * CommunicationController 类负责处理与通信相关的 RESTful API 请求
 * 它通过 @RestController 注解标识为一个控制器，并通过 @RequestMapping 指定请求的基路径
 */
@RestController
@RequestMapping("/api/communication")
public class CommunicationController {

    /**
     * 自动注入 CommunicationService 实例，用于处理通信相关的业务逻辑
     */
    @Autowired
    private CommunicationService communicationService;

    /**
     * 处理 produce 请求，生成信息
     *
     * @return 生成的信息字符串
     * @throws InterruptedException 如果在生成信息时发生中断异常
     */
    @GetMapping("/produce")
    public String produce() throws InterruptedException {
        return communicationService.produce();
    }

    /**
     * 处理 consume 请求，消费信息
     *
     * @return 消费的信息字符串
     * @throws InterruptedException 如果在消费信息时发生中断异常
     */
    @GetMapping("/consume")
    public String consume() throws InterruptedException {
        return communicationService.consume();
    }
}
