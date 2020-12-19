package org.acme.business.models;

import lombok.Getter;
import lombok.Setter;
import org.acme.business.controllers.BasicController;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "activities")
public class Activity extends SimpleModel<Activity> {

    private String name;

//    private Organizer organizer;
    private Date scheduledAt;
    private Integer duration;
    private String descr;
    @ManyToMany
    private List<Student> students;
    private Date createdAt;
    private String contact;

    @Override
    public void update(Activity another) {
        if (another.name != null) {
            name = another.name;
        }
        if (another.descr != null) {
            descr = another.descr;
        }
        if (another.getIsHidden() != null) {
            setIsHidden(another.getIsHidden());
        }
        if (another.contact != null) {
            contact = another.contact;
        }
    }

    @Override
    public Activity createShortModel() {
        Activity activity = new Activity();
        activity.setId(getId());
        return activity;
    }

    @Override
    public Activity createDefaultModel(Set<String> expands) {
        Activity activity = createShortModel();
        activity.setName(getName());
        activity.setDescr(getDescr());
        activity.setDuration(getDuration());
//        activity.organizer = BasicController.createModelByExpand(organizer, "organizer", expands);
        activity.students = BasicController.createModelsByExpand(students, "students", expands);
        return activity;
    }

}
