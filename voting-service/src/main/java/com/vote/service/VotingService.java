package com.vote.service;/*
 * Copyright (c) 2016 Matthew Fong
 */

import com.google.common.collect.ImmutableSet;
import com.vote.service.common.GuiceApplication;
import com.vote.service.common.health.HealthCheck;
import com.vote.service.common.resource.Resource;
import com.vote.service.common.task.AdminTask;
import com.vote.service.resource.PollResource;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VotingService extends GuiceApplication<VotingServiceConfig> {

    public static void main(String[] args) {
        try {
            new VotingService().run(args);
        } catch (Exception e) {
            log.error("FATAL ERROR", e);
        }
    }

    @Override
    protected VotingServiceModule createModule(VotingServiceConfig config) {
        return new VotingServiceModule(config);
    }

    @Override
    protected ImmutableSet<Class<? extends Resource>> getResources(VotingServiceConfig config) {
        return ImmutableSet.of(
                PollResource.class
        );
    }

    @Override
    protected ImmutableSet<Class<? extends HealthCheck>> getHealthChecks(VotingServiceConfig config) {
        return ImmutableSet.of(

        );
    }

    @Override
    protected ImmutableSet<Class<? extends AdminTask>> getAdminTasks(VotingServiceConfig config) {
        return ImmutableSet.of(

        );
    }

    @Override
    protected ImmutableSet<Class<? extends Managed>> getManagedTasks(VotingServiceConfig config) {
        return ImmutableSet.of(

        );
    }
}
