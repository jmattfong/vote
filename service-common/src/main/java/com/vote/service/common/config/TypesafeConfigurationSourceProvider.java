/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common.config;

import io.dropwizard.configuration.ConfigurationSourceProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TypesafeConfigurationSourceProvider implements ConfigurationSourceProvider {

    @Override
    public InputStream open(String path) throws IOException {
        String json = TypesafeConfigParser.loadFileAsJsonString(path);
        return new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
    }
}
