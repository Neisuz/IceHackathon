package org.acme.business.controllers;

import org.acme.business.models.SimpleModel;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

public class BasicController {

    @Context
    protected UriInfo ui;

    protected Set<String> getExpandsFromRequest() {
        return getSplittedParams("expand");
    }

    protected Set<String> getSplittedParams(String params) {
        String paramValue = getRequestParamAsString(params);
        return split(paramValue);
    }

    protected String getRequestParamAsString(String name) {
        String value = getQueryParams().getFirst(name);
        return value != null ? URLDecoder.decode(value, StandardCharsets.UTF_8) : null;
    }

    protected MultivaluedMap<String, String> getQueryParams() {
        return ui.getQueryParameters(false);
    }

    public static Set<String> split(String value) {
        return split(value, ',');
    }

    public static Set<String> split(String value, char splitter) {
        if (StringUtils.isBlank(value)) {
            return emptySet();
        }
        value = deleteWhitespace(value);
        return newSet(StringUtils.split(value, splitter));
    }

    @SafeVarargs
    public static <T> Set<T> newSet(T... values) {
        return Arrays.stream(values).collect(toSet());
    }

    public static <T> List<T> createModelsByExpand(
            List<? extends SimpleModel<T>> models, String expandCategoryName, Set<String> expandCategories) {

        return models == null || models.isEmpty() || expandCategories == null
                ? emptyList()
                : models.stream()
                .map(m -> createModelByExpand(m, expandCategoryName, expandCategories))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T> T createModelByExpand(
            SimpleModel<T> shortable, String expandCategoryName, Set<String> expandables) {

        if (shortable == null) {
            return null;
        }

        return expandables == null || !expandables.contains(expandCategoryName)
                ? createShortModel(shortable)
                : produceDefaultModel(shortable, expandables);
    }


    public static <T> T createShortModel(SimpleModel<T> model) {
        return model != null ? model.createShortModel() : null;
    }

    public static <T> T produceDefaultModel(SimpleModel<T> model, Set<String> expandables) {
        return model != null ? model.createDefaultModel(expandables) : null;
    }
}
