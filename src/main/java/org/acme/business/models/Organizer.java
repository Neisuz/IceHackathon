package org.acme.business.models;

import lombok.Getter;
import lombok.Setter;
import org.acme.business.controllers.BasicController;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "organizers")
public class Organizer extends SimpleModel<Organizer>{

    private String name;

//    private List<Activity> activities;
    private String email;
    private String contact;

    @OneToOne
    private Student student;

    @Override
    public void update(Organizer another) {
        if (another.email != null) {
            email = another.email;
        }
        if (another.name != null) {
            name = another.name;
        }
        if (another.contact != null) {
            contact = another.contact;
        }
    }

    @Override
    public Organizer createShortModel() {
        Organizer organizer = new Organizer();
        organizer.setId(getId());
        return organizer;
    }

    @Override
    public Organizer createDefaultModel(Set<String> expands) {
        Organizer organizer = createShortModel();
        organizer.name = name;
        organizer.contact = contact;
//        organizer.activities = BasicController.createModelsByExpand(activities, "activities", expands);
        return organizer;
    }
}
