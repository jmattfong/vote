/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.retry.exception;

public class RetryFailedRuntimeException extends NotRetryableRuntimeException {

    public RetryFailedRuntimeException(String message) {
        super(message);
    }

    public RetryFailedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryFailedRuntimeException(Throwable cause) {
        super(cause);
    }
}
