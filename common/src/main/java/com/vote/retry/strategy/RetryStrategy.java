/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.retry.strategy;

public interface RetryStrategy {

    int DEFAULT_MAX_EXECUTE_COUNT = 5;
    int DEFAULT_MIN_DELAY_MS = 10;
    int DEFAULT_MAX_DELAY_MS = 30000; // Thirty seconds

    int getRetryDelayMs(int count);

    default int getMaxExecuteCount() {
        return DEFAULT_MAX_EXECUTE_COUNT;
    }

    default int getMinDelayMs() {
        return DEFAULT_MIN_DELAY_MS;
    }

    default int getMaxDelayMs() {
        return DEFAULT_MAX_DELAY_MS;
    }
}
