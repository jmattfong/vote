/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.retry;

import com.vote.retry.exception.NotRetryableException;
import com.vote.retry.exception.NotRetryableRuntimeException;
import com.vote.retry.strategy.ExponentialBackoff;
import com.vote.retry.strategy.RetryStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Retry {

    public static void executeWithRetry(RetryableTask task) {
        executeWithRetry(() -> {
            task.execute();
            return null;
        });
    }

    public static void executeWithRetry(RetryableTask task, RetryStrategy strategy) throws NotRetryableException {
        executeWithRetry(() -> {
            task.execute();
            return null;
        }, strategy);
    }

    public static <T> T executeWithRetry(RetryableCallable<T> callable) {
        try {
            return executeWithRetry(callable, ExponentialBackoff.builder().build());
        } catch (NotRetryableException e) {
            throw new NotRetryableRuntimeException(e);
        }
    }

    public static <T> T executeWithRetry(RetryableCallable<T> callable, RetryStrategy strategy) throws NotRetryableException {
        int executionCount = 0;
        Exception caughtException;

        do {
            // increment executionCount first because the execute() call might throw an exception. This creates a
            // 1 indexed call count.
            executionCount++;

            try {
                return callable.execute();
            } catch (NotRetryableRuntimeException | NotRetryableException e) {
                throw e;
            } catch (Exception e) {
                log.warn("Attempt " + executionCount + " failed with exception", e);
                caughtException = e;
            }

            if (executionCount < strategy.getMaxExecuteCount()) {
                try {
                    wait(strategy, executionCount);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    throw new NotRetryableException("Thread was interrupted", e);
                }
            }

        } while (executionCount < strategy.getMaxExecuteCount());

        throw new NotRetryableException("All retries failed", caughtException);
    }

    private static void wait(RetryStrategy strategy, int executionCount) throws InterruptedException {
        int retryDelayMs = strategy.getRetryDelayMs(executionCount);

        if (retryDelayMs < strategy.getMinDelayMs()) {
            retryDelayMs = strategy.getMinDelayMs();
        } else if (retryDelayMs > strategy.getMaxDelayMs()) {
            retryDelayMs = strategy.getMaxDelayMs();
        }

        Thread.sleep(retryDelayMs);
    }
}
