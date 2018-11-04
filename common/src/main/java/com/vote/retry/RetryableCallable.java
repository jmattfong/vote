/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.retry;

@FunctionalInterface
public interface RetryableCallable<T> {

    T execute() throws Exception;
}
