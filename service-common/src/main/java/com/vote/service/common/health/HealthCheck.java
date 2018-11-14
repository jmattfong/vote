/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common.health;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class HealthCheck extends com.codahale.metrics.health.HealthCheck {

    @Getter
    private final String name;
}
