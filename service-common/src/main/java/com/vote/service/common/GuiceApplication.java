/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.vote.service.common.config.TypesafeConfigurationSourceProvider;
import com.vote.service.common.exception.ApplicationExceptionMapper;
import com.vote.service.common.exception.RenderableExceptionMapper;
import com.vote.service.common.health.HealthCheck;
import com.vote.service.common.resource.Resource;
import com.vote.service.common.task.AdminTask;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.ext.ExceptionMapper;

@Slf4j
public abstract class GuiceApplication<ConfigT extends GuiceApplicationConfig> extends Application<ConfigT> {

    protected abstract Module createModule(ConfigT config);

    protected abstract ImmutableSet<Class<? extends Resource>> getResources(ConfigT config);

    protected abstract ImmutableSet<Class<? extends HealthCheck>> getHealthChecks(ConfigT config);

    protected ImmutableSet<Class<? extends AdminTask>> getAdminTasks(ConfigT config) {
        return ImmutableSet.of();
    }

    protected ImmutableSet<Class<? extends Managed>> getManagedTasks(ConfigT config) {
        return ImmutableSet.of();
    }

    protected ImmutableSet<Class<? extends ExceptionMapper>> getExceptionMappers(ConfigT config) {
        return ImmutableSet.of(
                RenderableExceptionMapper.class,
                ApplicationExceptionMapper.class
        );
    }

    private void resolveSecrets(ConfigT config) {
        // TODO resolve secrets
    }

    @Override
    public void initialize(Bootstrap<ConfigT> bootstrap) {
        // TODO add typesafe config parser

        bootstrap.setConfigurationSourceProvider(new TypesafeConfigurationSourceProvider());
    }

    @Override
    public void run(ConfigT configuration, Environment environment) throws Exception {
        try {
            resolveSecrets(configuration);

            Module module = createModule(configuration);

            Injector injector = Guice.createInjector(module);

            bindResources(configuration, injector, environment);
            bindHealthChecks(configuration, injector, environment);
            bindAdminTasks(configuration, injector, environment);
            bindManagedTasks(configuration, injector, environment);
        } catch (Exception e) {
            log.error("Error during startup", e);
            throw e;
        }
    }

    private void bindResources(ConfigT config, Injector injector, Environment environment) {
        ImmutableSet<Class<? extends Resource>> resources = getResources(config);
        if (resources != null) {
            for (Class<? extends Resource> resourceClass : resources) {
                Resource resource = injector.getInstance(resourceClass);
                environment.jersey().register(resource);
            }
        }
    }

    private void bindHealthChecks(ConfigT config, Injector injector, Environment environment) {
        ImmutableSet<Class<? extends HealthCheck>> healthChecks = getHealthChecks(config);
        if (healthChecks != null) {
            for (Class<? extends HealthCheck> healthCheckClass : healthChecks) {
                HealthCheck healthCheck = injector.getInstance(healthCheckClass);
                environment.healthChecks().register(healthCheck.getName(), healthCheck);
            }
        }
    }

    private void bindAdminTasks(ConfigT config, Injector injector, Environment environment) {
        ImmutableSet<Class<? extends AdminTask>> adminTasks = getAdminTasks(config);
        if (adminTasks != null) {
            for (Class<? extends AdminTask> adminTaskClass : adminTasks) {
                AdminTask adminTask = injector.getInstance(adminTaskClass);
                environment.admin().addTask(adminTask);
            }
        }
    }

    private void bindManagedTasks(ConfigT config, Injector injector, Environment environment) {
        ImmutableSet<Class<? extends Managed>> managedTasks = getManagedTasks(config);
        if (managedTasks != null) {
            for (Class<? extends Managed> managedClass : managedTasks) {
                Managed managedTask = injector.getInstance(managedClass);
                environment.lifecycle().manage(managedTask);
            }
        }
    }
}
