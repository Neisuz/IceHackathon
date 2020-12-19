package org.acme.business.models;

import lombok.Getter;
import lombok.Setter;
import org.acme.business.controllers.BasicController;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "services")
public class Service extends SimpleModel<Service> {

    private String name;
    private Float price;
    private Integer duration;
//    private Tutor tutor;

    @Override
    public void update(Service another) {
        if (another.name != null) {
            name = another.name;
        }
        if (another.price != null) {
            price = another.price;
        }
        if (another.duration != null) {
            duration = another.duration;
        }
        if (another.getIsHidden() != null) {
            setIsHidden(another.getIsHidden());
        }
    }

    @Override
    public Service createShortModel() {
        Service service = new Service();
        service.setId(getId());
        return service;
    }

    @Override
    public Service createDefaultModel(Set<String> expands) {
        Service service = createShortModel();
        service.setName(getName());
        service.setDuration(getDuration());
        service.setPrice(getPrice());
//        service.tutor = BasicController.createModelByExpand(tutor, "tutor",expands);
        return service;
    }
}
