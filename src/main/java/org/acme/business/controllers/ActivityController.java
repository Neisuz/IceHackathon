package org.acme.business.controllers;

import lombok.extern.slf4j.Slf4j;
import org.acme.business.models.Activity;
import org.acme.business.models.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.io.InputStream;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@ApplicationScoped
@Path("/v1/activities/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ActivityController extends MainController {

    @GET
    @Path("{id}")
    public Response show(@PathParam("id") Long id) throws Exception {
        return show(id, Student.class);
    }
    // todo
//    @GET
//    public Response listAll() {
//        return...
//    }

    @POST
    @Transactional
    public Response create(InputStream body) throws Exception {
        return create(Activity.class, body);
    }
// todo
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
        return update(id, Activity.class,body);
    }

}
