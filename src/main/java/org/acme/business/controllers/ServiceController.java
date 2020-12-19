//package org.acme.business.controllers;
//
//
//import lombok.extern.slf4j.Slf4j;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.transaction.Transactional;
//import javax.ws.rs.*;
//import javax.ws.rs.core.Response;
//
//import java.io.InputStream;
//
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
//
//@Slf4j
//@ApplicationScoped
//@Path("/v1/services/")
//@Produces(APPLICATION_JSON)
//@Consumes(APPLICATION_JSON)
//public class ServiceController {
//
//    @GET
//    @Path("{id}")
//    public Response show() {
//
//        return renderJSON(resourceService.showById(context));
//    }
//
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
//}
