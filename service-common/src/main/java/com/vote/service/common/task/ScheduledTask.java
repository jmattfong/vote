/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common.task;

import io.dropwizard.lifecycle.Managed;

public class ScheduledTask implements Managed {

    @Override
    public void start() throws Exception {
        // TODO implement a task that executes regularly in the background
    }

    @Override
    public void stop() throws Exception {
        // TODO shutdown any executors that were created
    }
}
