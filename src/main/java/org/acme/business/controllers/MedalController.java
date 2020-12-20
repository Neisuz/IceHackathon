package org.acme.business.controllers;

import lombok.extern.slf4j.Slf4j;
import org.acme.business.models.Medal;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.io.InputStream;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@ApplicationScoped
@Path("/v1/medals/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class MedalController extends MainController {

    @GET
    @Path("{id}")
    public Response show(@PathParam("id") Long id) throws Exception {
        return show(id, Medal.class);
    }

//    @GET
//    public Response listAll() {
//        return...
//    }

    @POST
    @Transactional
    public Response create(InputStream body) throws Exception {
        return create(Medal.class, body);
    }
//
//    @DELETE
//    @Path("{id}")
//    @Transactional
//    public Response delete(@PathParam("id") Long id) {
//        return delete(Student.class, id);
//    }

    @POST
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, InputStream body) throws Exception {
        return update(id, Medal.class,body);
    }

}
