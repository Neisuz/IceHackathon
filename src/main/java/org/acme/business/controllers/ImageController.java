package org.acme.business.controllers;

import lombok.extern.slf4j.Slf4j;
import org.acme.business.models.Image;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@ApplicationScoped
@Path("/v1/images/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ImageController extends MainController {

    @GET
    @Path("{id}")
    public Response show(@PathParam("id") Long id) throws Exception {
        return show(id, Image.class);
    }

}
