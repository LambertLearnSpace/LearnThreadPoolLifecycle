package com.learn.aegis.service.impl;

import com.learn.aegis.service.CommunicationService;
import org.springframework.stereotype.Service;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现CommunicationService接口的服务类，提供消息的生产和消费功能
 * 使用ArrayBlockingQueue作为消息队列，确保线程安全
 */
@Service
@Slf4j
public class CommunicationServiceImpl implements CommunicationService {

    // 消息队列，容量固定为10
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    /**
     * 生产消息并放入队列
     *
     * @return 生产消息的结果字符串
     * @throws InterruptedException 如果线程被中断
     */
    @Override
    public String produce() throws InterruptedException {
        // 将消息放入队列，如果队列已满，则阻塞等待
        queue.put("item");
        log.info("Produced an item.");
        return "Produced an item";
    }

    /**
     * 从队列中消费消息
     *
     * @return 消费消息的结果字符串，包含消费的消息内容
     * @throws InterruptedException 如果线程被中断
     */
    @Override
    public String consume() throws InterruptedException {
        // 从队列中取出消息，如果队列为空，则阻塞等待
        String item = queue.take();
        log.info("Consumed an item: {}", item);
        return "Consumed an item: " + item;
    }
}
