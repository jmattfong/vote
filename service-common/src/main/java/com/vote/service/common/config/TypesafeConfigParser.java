/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;

import java.io.File;
import java.io.IOException;

public class TypesafeConfigParser {

    public static String loadFileAsJsonString(String path) throws IOException {
        Config config = ConfigFactory.parseFile(new File(path));
        return config.root().render(
                ConfigRenderOptions.defaults().setJson(true));
    }
}
