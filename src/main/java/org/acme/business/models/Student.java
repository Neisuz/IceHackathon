package org.acme.business.models;

import lombok.Getter;
import lombok.Setter;
import org.acme.business.controllers.BasicController;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends SimpleModel<Student> {

    private String name;

    @OneToOne
    private Organizer organizer;

    @OneToOne
    private Tutor tutor;

    @Transient
    private String password;

    @Transient
    private String passwordAgain;

    private String login;
    private Date birthday;
    private String email;
    private String phone;

    // todo: when activity and achievments are available
//    @ManyToMany
//    private List<Activity> activities;

//    @ManyToMany(mappedBy = "tutor")
//    private List<Medal> medals;

    @Override
    public void update(Student another) {
        if (another.name != null) {
            name = another.name;
        }
        if (another.birthday != null) {
            birthday = another.birthday;
        }
        if (another.email != null) {
            email = another.email;
        }
        if (another.phone != null) {
            phone = another.phone;
        }
    }

    @Override
    public Student createShortModel() {
        Student student = new Student();
        student.setId(getId());
        return student;
    }

    @Override
    public Student createDefaultModel(Set<String> expands) {
        Student student = createShortModel();
        student.setName(getName());
        student.setLogin(getLogin());
        student.setEmail(getEmail());
        student.setPhone(getPhone());
        student.organizer = BasicController.createModelByExpand(organizer, "organizer", expands);
        student.tutor = BasicController.createModelByExpand(tutor, "tutor", expands);
//        student.activities = BasicController.createModelsByExpand(activities, "activities", expands);
//        student.medals = BasicController.createModelsByExpand(medals, "medals", expands);
        return student;
    }

}
