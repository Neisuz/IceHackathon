package org.acme.business.utils;

import org.acme.business.models.SimpleModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class ModelUtils {

    @Inject
    EntityManager em;

    public <T extends SimpleModel<T>> T findModelById(Long id, Class<T> model) {
        List<T> allModelsByIds = findAllModelsByIds(Set.of(id), model);

        if (allModelsByIds.isEmpty()) {
            System.out.println( " there is no model" + model.getSimpleName() + id);
        }
        return allModelsByIds.get(0);
    }

    private <T extends SimpleModel<T>> List<T> findAllModelsByIds(Set<Long> ids, Class<T> clazz) {

//        String extraPart = includeHidden ? " and (x.isHidden is null or x.isHidden = false)" : "";
        String q = String.format(
                "select x from %s x where x.id in (:ids)", clazz.getSimpleName());
        TypedQuery<T> query = em.createQuery(q, clazz).setParameter("ids", ids);
        return query.getResultList();
    }
}
