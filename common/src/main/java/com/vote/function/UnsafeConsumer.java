/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.function;

@FunctionalInterface
public interface UnsafeConsumer<T, E extends Exception> {

    void accept(T params) throws E;
}
