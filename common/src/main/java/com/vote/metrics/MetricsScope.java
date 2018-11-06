/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.metrics;

public final class MetricsScope implements AutoCloseable {

    private final String name;
    private final boolean autoTimer;
    private final boolean autoFault;
    private final long startTime;

    private boolean success = false;

    public static MetricsScope create(String ... names) {
        return new MetricsScope(String.join(".", names), true, true);
    }

    public static MetricsScope create(boolean autoTimer, boolean autoFault, String ... names) {
        return new MetricsScope(String.join(".", names), autoTimer, autoFault);
    }

    public MetricsScope(String name, boolean autoTimer, boolean autoFault) {
        this.name = name;
        this.autoTimer = autoTimer;
        this.autoFault = autoFault;

        this.startTime = autoTimer ? System.currentTimeMillis() : 0;
    }

    public void recordSuccess() {
        success = true;
    }

    public void emit(String name, double value) {
        // TODO actually emit some metrics
    }

    @Override
    public void close() {
        if (autoTimer) {
            emit(name + ".Time", System.currentTimeMillis() - startTime);
        }

        if (autoFault) {
            emit(name + ".Fault", success ? 0 : 1);
        }
    }
}
