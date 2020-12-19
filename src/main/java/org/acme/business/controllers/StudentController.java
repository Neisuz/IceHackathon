package org.acme.business.controllers;


import lombok.extern.slf4j.Slf4j;
import org.acme.business.models.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@ApplicationScoped
@Path("/v1/students/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class StudentController extends MainController {

    @GET
    @Path("{id}")
    public Response show(@PathParam("id") Long id) {
        return show(id, Student.class);
    }

//    @GET
////    @SecurityCheck({PARTNER, PARTNER_EXTEND, PARTNER_SIMPLIFIED, AGENT, PUBLIC, PUBLIC_VIP, CALL_CENTER_EMPLOYEE, MANAGER})
//    public Response listAll() {
//        return
//    }
//
//    @POST
//    @Transactional
////    @SecurityCheck({PARTNER, PARTNER_EXTEND, PARTNER_SIMPLIFIED, AGENT, PUBLIC_VIP, CALL_CENTER_EMPLOYEE, MANAGER})
//    public Response create(InputStream body) {
//
//
//        return create(HibernateResource.class, x -> x.setIsOnline(false), this::afterCreate, body);
//    }
//
//    @POST
//    @Path("{id}")
//    @Transactional
////    @SecurityCheck({PARTNER, PARTNER_EXTEND, PARTNER_SIMPLIFIED, AGENT, PUBLIC_VIP, CALL_CENTER_EMPLOYEE, MANAGER})
//    public Response delete() {
//
//
//        return create(HibernateResource.class, x -> x.setIsOnline(false), this::afterCreate, body);
//    }
//
//    @POST
//    @Path("{id}")
//    @Transactional
////    @SecurityCheck({PARTNER, PARTNER_EXTEND, PARTNER_SIMPLIFIED, AGENT, PUBLIC_VIP, CALL_CENTER_EMPLOYEE, MANAGER})
//    public Response update(InputStream body) {
//
//
//        return create(HibernateResource.class, x -> x.setIsOnline(false), this::afterCreate, body);
//    }
}
