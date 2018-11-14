/*
 * Copyright (c) 2016 Matthew Fong
 */

package com.vote.service.resource;

import com.vote.service.common.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("poll")
public class PollResource extends Resource {

    @POST
    public Response createPoll(String body) {
        return Response.ok()
                .entity(body)
                .build();
    }

    @GET
    @Path("{id}")
    public Response getPoll(@PathParam("id") String id) {
        return Response.ok()
                .entity(id)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updatePoll(@PathParam("id") String id, String body) {
        return Response.ok()
                .entity(body)
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePoll(@PathParam("id") String id) {
        return Response.ok()
                .entity(id)
                .build();
    }
}
