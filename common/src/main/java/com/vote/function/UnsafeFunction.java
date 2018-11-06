/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.function;

@FunctionalInterface
public interface UnsafeFunction<T, R, E extends Exception> {

    R apply(T args) throws E;
}
