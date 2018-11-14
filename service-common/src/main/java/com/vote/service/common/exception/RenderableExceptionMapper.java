/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RenderableExceptionMapper implements ExceptionMapper<RenderableException> {

    @Override
    public Response toResponse(RenderableException e) {
        return Response.status(e.getStatusCode())
                .entity(e.getMessage())
                .build();
    }
}
