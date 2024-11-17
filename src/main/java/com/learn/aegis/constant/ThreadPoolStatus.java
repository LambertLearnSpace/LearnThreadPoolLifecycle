package com.learn.aegis.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ThreadPoolStatus {
    RUNNING("Running"),
    SHUTDOWN("Shutdown"),
    STOP("Stop"),
    TIDYING("Tidying"),
    TERMINATED("Terminated");

    private final String description;
}