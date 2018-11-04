/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.retry.strategy;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Random;

/**
 * When all retries fail, the default strategy will take about 1 second plus the time taken by the task itself.
 */
@Value
@Builder
public class LinearBackoff implements RetryStrategy {

    public static final int DEFAULT_INITIAL_BACKOFF_MS = 150;
    public static final int DEFAULT_MAX_JITTER_MS = 100;

    @Getter
    @Builder.Default
    public int maxExecuteCount = DEFAULT_MAX_EXECUTE_COUNT;

    @Getter
    @Builder.Default
    public int minDelayMs = DEFAULT_MIN_DELAY_MS;

    @Getter
    @Builder.Default
    public int maxDelayMs = DEFAULT_MAX_DELAY_MS;

    @Builder.Default
    public int initialBackoffAmountMs = DEFAULT_INITIAL_BACKOFF_MS;

    @Builder.Default
    public int maxJitterMs = DEFAULT_MAX_JITTER_MS;

    @NonNull
    @Builder.Default
    public Random random = new Random();

    @Override
    public int getRetryDelayMs(int retryCount) {
        return retryCount * initialBackoffAmountMs + random.nextInt(maxJitterMs);
    }
}
