/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.function;

@FunctionalInterface
public interface UnsafeRunnable<E extends Exception> {

    void run() throws E;
}
