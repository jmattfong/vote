/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.function;

@FunctionalInterface
public interface UnsafeBiFunction<T, U, R, E extends Exception> {

    R apply(T arg1, U arg2) throws E;
}
