package com.learn.aegis.service;

/**
 * SynchronizationService 接口提供了两种方法来增加计数值。
 * 该接口用于演示在多个线程访问共享资源时实现线程安全的不同方式。
 */
public interface SynchronizationService {

    /**
     * 增加计数值，不使用显式锁机制。
     * 此方法可能不是线程安全的，在多个线程并发访问时可能导致计数值错误。
     *
     * @return 增加后的新的计数值。
     */
    int increaseCount();

    /**
     * 使用显式锁机制增加计数值以确保线程安全。
     * 此方法设计用于防止多个线程同时修改计数，确保计数值的准确性。
     *
     * @return 增加后的新的计数值。
     */
    int increaseCountWithLock();
}
