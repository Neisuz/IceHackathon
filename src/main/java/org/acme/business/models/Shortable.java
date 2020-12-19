package org.acme.business.models;

import java.util.Set;

public interface Shortable<T> {

    T createShortModel();

    T createDefaultModel(Set<String> expands);
}
