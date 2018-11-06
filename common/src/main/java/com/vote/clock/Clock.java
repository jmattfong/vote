/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.clock;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public final class Clock {

    public long getCurrentTimeMs() {
        // TODO use something other than the system time. DB time?
        return System.currentTimeMillis();
    }
}
