/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common.exception;

import lombok.Getter;

public class RenderableException extends Exception {

    @Getter
    private final int statusCode;

    public RenderableException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public RenderableException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
