/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.retry.exception;

public class RetryFailedException extends NotRetryableException {

    public RetryFailedException(String message) {
        super(message);
    }

    public RetryFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryFailedException(Throwable cause) {
        super(cause);
    }
}
