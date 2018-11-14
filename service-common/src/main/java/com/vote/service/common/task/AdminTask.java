/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common.task;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

public class AdminTask extends Task {

    /**
     * Create a new task with the given name.
     *
     * @param name the task's name
     */
    protected AdminTask(String name) {
        super(name);
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
        // TODO implement something to make the task API easier to use
    }
}
