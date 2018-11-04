package com.vote.retry;

@FunctionalInterface
public interface RetryableTask {

    void execute() throws Exception;
}
