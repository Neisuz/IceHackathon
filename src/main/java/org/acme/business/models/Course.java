package org.acme.business.models;


import lombok.Getter;
import lombok.Setter;
import org.acme.business.controllers.BasicController;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course extends SimpleModel<Course>{

    private String name;
    private Date createAt;
    private String format;
//    private List<Student> students;
    private Date scheduled_to;
//    private Organizer organizer;

    @Override
    public void update(Course another) {
        if (another.name != null) {
            name = another.name;
        }
        if (another.format != null) {
            format = another.format;
        }

    }

    @Override
    public Course createShortModel() {
        Course course = new Course();
        course.setId(getId());
        return course;
    }

    @Override
    public Course createDefaultModel(Set<String> expands) {
       Course course = createShortModel();
       course.setFormat(getFormat());
       course.setName(getName());
//       course.organizer = BasicController.createModelByExpand(organizer,"organizer", expands);
        return null;
    }

}
