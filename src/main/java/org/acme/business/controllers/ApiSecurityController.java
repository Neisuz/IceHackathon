package org.acme.business.controllers;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Path("/")
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_FORM_URLENCODED)
public class ApiSecurityController extends BasicController{

    @GET
    @Transactional
    @Path("signin/")
    public Response signin() throws Exception {
        String email = getRequestParamAsString("email");
        String password = getRequestParamAsString("password");

        getPasswordCheck(password);
        // todo: more checks
//        AuthToken authToken = getAuthTokenByPassword(email, password);
//        HibernateAuthToken tokenToRender = authToken.produceDefaultModel(getExpandablesFromRequest());
//        return renderJSON(tokenToRender);
        throw new UnsupportedOperationException("on the way");
    }

    // Строка должна быть не меньше 8 символов,
    // содержать как минимум 1 большую букву,
    // одну маленькую букву и ЛИБО спец. символ ЛИБО цифру
    public void getPasswordCheck(String password) throws Exception {

        String pat = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}";
        if (password.matches(pat)) {
            System.out.println("Пароль действителен");
        }
        else {
            throw new Exception("Пароль недействителен");
        }
    }


}
