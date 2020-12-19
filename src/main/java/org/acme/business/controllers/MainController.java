package org.acme.business.controllers;

import org.acme.business.models.SimpleModel;
import org.acme.business.utils.ModelUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Set;

import static org.acme.business.utils.ValidateUtils.validateNotNullParam;

public class MainController extends BasicController{

    @Inject
    EntityManager em;

    protected <T extends SimpleModel<T>> Response show(Long id, Class<T> model) {
        validateNotNullParam("id", id);

        T modelFromDb = findModelById(id, model);

//        Authtoken authToken = getAuthToken();
//        if ( isApiSecurityCheckRequired(authToken) ) {
//            modelFromDb.checkAccess(authToken);
//        }

        return renderJSON(modelFromDb.createDefaultModel(getExpandsFromRequest()));
    }

    protected Response renderJSON(Object o) {
        return Response.ok(o).build();
    }



    public <T extends SimpleModel<T>> T findModelById(Long id, Class<T> model) {
        List<T> allModelsByIds = findAllModelsByIds(Set.of(id), model);

        if (allModelsByIds.isEmpty()) {
            System.out.println( " there is no model" + model.getSimpleName() + id);
        }
        return allModelsByIds.get(0);
    }

    private <T extends SimpleModel<T>> List<T> findAllModelsByIds(Set<Long> ids, Class<T> clazz) {

//        String extraPart = " and (x.isHidden is null or x.isHidden = false)" : "";
        String q = String.format(
                "select x from %s x where x.id in (:ids)", clazz.getSimpleName());
        TypedQuery<T> query = em.createQuery(q, clazz).setParameter("ids", ids);
        return query.getResultList();
    }

}
