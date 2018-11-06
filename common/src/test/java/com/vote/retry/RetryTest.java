/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.retry;

import com.vote.retry.exception.NotRetryableRuntimeException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.xml.ws.Holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RetryTest {

    @Test
    public void testRetryCallableExecutesOnceOnSuccess() {
        Holder<Integer> counter = new Holder<>(0);
        Retry.executeWithRetry(() -> {
            counter.value++;
        }, "");

        assertEquals(1, (int) counter.value);
    }

    @Test
    public void testRetryTaskExecutesOnceOnSuccess() {
        Holder<Integer> counter = new Holder<>(0);
        Retry.executeWithRetry(() -> {
            counter.value++;
            return "waffles";
        }, "");

        assertEquals(1, (int) counter.value);
    }

    @Test
    public void testRetryTaskReturnsResult() {
        String result = Retry.executeWithRetry(() -> "waffles", "");

        assertEquals("waffles", result);
    }

    @Test
    public void testRetryTaskReturnsNull() {
        String result = Retry.executeWithRetry(() -> null, "");

        assertEquals(null, result);
    }

    @Test
    public void testRetryCallableThrowsCorrectExceptionAfterRetries() {
        Holder<Integer> counter = new Holder<>(0);

        try {
            Retry.executeWithRetry(() -> {
                counter.value++;
                throw new IllegalStateException();
            }, "");
            Assert.fail("We shouldn't have gotten here");
        } catch (NotRetryableRuntimeException e) {
            assertTrue(e.getCause() instanceof IllegalStateException);
        }

        assertEquals(Retry.DEFAULT_STRATEGY.getMaxExecuteCount(), (int) counter.value);
    }

    @Test
    public void testRetryTaskThrowsCorrectExceptionAfterRetries() {
        Holder<Integer> counter = new Holder<>(0);

        try {
            Retry.executeWithRetry((RetryableTask) () -> {
                counter.value++;
                throw new IllegalStateException();
            }, "");
            Assert.fail("We shouldn't have gotten here");
        } catch (NotRetryableRuntimeException e) {
            assertTrue(e.getCause() instanceof IllegalStateException);
        }

        assertEquals(Retry.DEFAULT_STRATEGY.getMaxExecuteCount(), (int) counter.value);
    }
}
