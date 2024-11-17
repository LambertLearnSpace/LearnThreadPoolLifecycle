package com.learn.aegis.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ThreadPoolType {
    FIXED("Fixed Thread Pool"),
    CACHED("Cached Thread Pool"),
    SINGLE("Single Thread Pool"),
    SCHEDULED("Scheduled Thread Pool"),
    WORK_STEALING("Work Stealing Pool");

    private final String description;
}