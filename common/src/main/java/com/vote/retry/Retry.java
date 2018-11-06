/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.retry;

import com.vote.metrics.MetricsScope;
import com.vote.retry.exception.NotRetryableException;
import com.vote.retry.exception.NotRetryableRuntimeException;
import com.vote.retry.exception.RetryFailedException;
import com.vote.retry.exception.RetryFailedRuntimeException;
import com.vote.retry.strategy.ExponentialBackoff;
import com.vote.retry.strategy.RetryStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Retry {

    public static final RetryStrategy DEFAULT_STRATEGY = ExponentialBackoff.builder().build();

    public static void executeWithRetry(RetryableTask task, String... metricNames) {
        executeWithRetry(toCallable(task), metricNames);
    }

    public static void executeWithRetry(RetryableTask task) throws NotRetryableException {
        executeWithRetry(toCallable(task));
    }

    public static void executeWithRetry(RetryableTask task, RetryStrategy strategy, String... metricNames) {
        executeWithRetry(toCallable(task), strategy, metricNames);
    }

    public static void executeWithRetry(RetryableTask task, RetryStrategy strategy) throws NotRetryableException {
        executeWithRetry(toCallable(task), strategy);
    }

    public static <T> T executeWithRetry(RetryableCallable<T> callable, String... metricNames) {
        try (MetricsScope scope = MetricsScope.create(metricNames)) {
            T result = executeWithRetry(callable);
            scope.recordSuccess();
            return result;
        } catch (NotRetryableException e) {
            throw new NotRetryableRuntimeException(e.getMessage(), e.getCause());
        }
    }

    public static <T> T executeWithRetry(RetryableCallable<T> callable) throws NotRetryableException {
        return executeWithRetry(callable, DEFAULT_STRATEGY);
    }

    public static <T> T executeWithRetry(RetryableCallable<T> callable, RetryStrategy strategy, String... metricNames) {
        try (MetricsScope scope = MetricsScope.create(metricNames)) {
            T result = executeWithRetry(callable, strategy);
            scope.recordSuccess();
            return result;
        } catch (RetryFailedException e) {
            throw new RetryFailedRuntimeException(e.getMessage(), e.getCause());
        } catch (NotRetryableException e) {
            throw new NotRetryableRuntimeException(e.getMessage(), e.getCause());
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

        throw new RetryFailedException("All retries failed", caughtException);
    }

    private static RetryableCallable<?> toCallable(RetryableTask task) {
        return () -> {
            task.execute();
            return null;
        };
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
