/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VotingServiceModule extends AbstractModule {

    private final VotingServiceConfig config;

    @Override
    protected void configure() {
        bind(VotingServiceConfig.class).toInstance(config);
    }
}
