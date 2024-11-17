package com.learn.aegis.service;

/**
 * 定义了一个通信服务接口，用于模拟消息的生产和消费过程
 * 这个接口主要用于需要进行消息传递和处理的系统中，通过生产和消费方法来模拟消息的发送和接收
 */
public interface CommunicationService {
    /**
     * 生产消息的方法
     * 该方法负责生成或获取一条消息，并可能需要将其放入一个消息队列中
     *
     * @return 生产的消息字符串
     * @throws InterruptedException 如果生产消息的过程中被中断，则抛出此异常
     */
    String produce() throws InterruptedException;

    /**
     * 消费消息的方法
     * 该方法负责从消息队列中取出并处理一条消息，模拟消息的消费过程
     *
     * @return 消费的消息字符串
     * @throws InterruptedException 如果消费消息的过程中被中断，则抛出此异常
     */
    String consume() throws InterruptedException;
}
