package org.acme.business.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.business.models.SimpleModel;
import org.acme.business.utils.TransformationUtils;
import org.hibernate.Session;
import org.jooq.DSLContext;
import org.jooq.Table;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.acme.business.utils.TransformationUtils.*;
import static org.acme.business.utils.ValidateUtils.validateNotNullParam;
import static org.jooq.impl.DSL.field;
import static org.jooq.lambda.Unchecked.supplier;

public class MainController extends BasicController {

    @Inject
    EntityManager em;

    @Inject
    Validator validator;

    @Inject
    ObjectMapper jsonMapper;

    protected <T extends SimpleModel<T>> Response show(Long id, Class<T> model) throws Exception {
        validateNotNullParam("id", id);

        T modelFromDb = findModelById(id, model);

        // todo: check validation of models

        return renderJSON(modelFromDb.createDefaultModel(getExpandsFromRequest()));
    }

    protected <T extends SimpleModel<T>> Response create(
            Class<T> clazz, InputStream body) throws Exception {

        String bodyFromRequest = getBodyFromRequest(body);

        T model = deserializeFromRequest(clazz, bodyFromRequest);

        validate(model);
        create(model);

        return getModelAfterPostWithExpands(model, clazz);
    }

    protected Response renderJSON(Object o) {
        return Response.ok(o).build();
    }

    public <T extends SimpleModel<T>> T findModelById(Long id, Class<T> model) {
        List<T> allModelsByIds = findAllModelsByIds(Set.of(id), model);

        if (allModelsByIds.isEmpty()) {
            System.out.println(" there is no model" + model.getSimpleName() + id);
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

    public <T extends SimpleModel<T>> void validate(T model) {

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(model);
        if (!constraintViolations.isEmpty()) {
            String message = constraintViolations.iterator().next().getMessage();
            System.out.println(" " + message + " ");
        }
    }

    public <T extends SimpleModel<T>> void create(T model) {
        save(model);
    }

    public <T extends SimpleModel<T>> void update(T model) {
        em.merge(model);
        em.flush();
    }

    public Session getHibernateSession() {
        return (Session) em.getDelegate();
    }

    public <T extends SimpleModel<T>> void save(T model) {
        getHibernateSession().saveOrUpdate(model);
    }

    protected <T> T deserializeFromRequest(Class<T> clazz, String body) throws Exception {
        T t = jsonMapper.readValue(body, clazz);
        if (t == null) {
            throw new Exception("missing from the request");
        }
        return t;
    }

    protected <T extends SimpleModel<T>> Response getModelAfterPostWithExpands(T model, Class<T> clazz) {
        Set<String> expandables = getExpandsFromRequest();
        T dbModel = flushAndRefresh(model, clazz);
        T defaultModel = produceDefaultModel(dbModel, expandables);
        return renderJSON(defaultModel, Response.Status.OK);
    }

    protected Response renderJSON(Object o, Response.Status status) {
        return Response.ok(o).status(status).build();
    }

    public <T extends SimpleModel<T>> T refresh(T model, Class<T> clazz) {
        if (em.contains(model)) {
            em.refresh(model);
            return model;
        } else {
            return findModelById(model.getId(), clazz);
        }
    }

    public <T extends SimpleModel<T>> T flushAndRefresh(T model, Class<T> clazz) {
        em.flush();
        em.clear();
        return refresh(model, clazz);
    }

    protected <T extends SimpleModel<T>> Response update(
            Long id, Class<T> clazz, InputStream body) throws Exception {

        validateNotNullParam("id", id);

        String bodyFromRequest = getBodyFromRequest(body);
        JsonNode node = stringToNode(jsonMapper, bodyFromRequest);

        T modelFromRequest = nodeToObject(jsonMapper, node, clazz);
        modelFromRequest.setId(id);

        T modelFromDb = getModelFromDbAndCheckAccess(id, clazz);

        T dbObject = validateAndUpdate(modelFromRequest, modelFromDb, node);

        return getModelAfterPostWithExpands(dbObject, clazz);
    }

    protected <T extends SimpleModel<T>> T getModelFromDbAndCheckAccess(
            Long id, Class<T> clazz) {

        T model = findModelById(id, clazz);

        // one more validation and check

        return model;
    }

    public <T extends SimpleModel<T>> T validateAndUpdate(
            T modelFromRequest, T modelFromDb, JsonNode node) throws Exception {

        Long id = modelFromRequest.getId();
        validateNotNullParam("id", id);

        if (modelFromDb == null) {
            modelFromDb = (T) findModelById(id, modelFromRequest.getClass());
        }

        modelFromDb.update(modelFromRequest);

        if (node != null) {
            modelFromDb.updateByRequestJsonNode(node);
        }

        validate(modelFromDb);
        update(modelFromDb);

        return modelFromDb;
    }

    protected <T extends SimpleModel<T>> Response delete(
            Long id,
            Class<T> clazz,
            Table table,
            boolean isForcedDelete) throws Exception {

        validateNotNullParam("id", id);

        T modelFromDb = findModelById(id, clazz);

        delete(Collections.singleton(id), table, isForcedDelete);

        return renderJSON(produceDefaultModel(modelFromDb,null));
    }

    public Set<Long> delete(Set<Long> ids, Table table, boolean isForceDelete) throws Exception {

        String query = String.format("UPDATE %s set is_hidden = true where id in %s", table, ids);
        try {
            int rows = em.createNativeQuery(query).executeUpdate();
            if (rows < 1) {
                throw new Exception("Entities to update not found.");
            }
        } catch (Exception e) {
            throw new Exception(e + "Can not delete entities [%s] from [%s]" + ids + table.getName());
        }
        return ids;
    }
}
