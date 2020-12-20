package org.acme.business.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image extends SimpleModel<Image> {

    private String url;

    @Override
    public void update(Image another) {
        if (another.url != null) {
            url = another.url;
        }
    }

    @Override
    public Image createShortModel() {
        Image image = new Image();
        image.setId(getId());
        return image;
    }

    @Override
    public Image createDefaultModel(Set<String> expands) {
        Image image = createShortModel();
        image.setUrl(getUrl());
        return image;
    }

}
