package org.acme.business.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SimpleModel<T> implements Shortable<T>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isHidden;

    public abstract void update(T simpleModel);

    public void updateByRequestJsonNode(JsonNode node) {}

}
