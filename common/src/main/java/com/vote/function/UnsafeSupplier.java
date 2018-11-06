/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.function;

@FunctionalInterface
public interface UnsafeSupplier<T, E extends Exception> {

    T get() throws E;
}
