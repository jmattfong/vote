/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApplicationExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return Response.status(500)
                .entity("Internal server error")
                .build();
    }
}
