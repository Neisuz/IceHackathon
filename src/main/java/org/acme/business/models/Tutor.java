package org.acme.business.models;

import lombok.Getter;
import lombok.Setter;
import org.acme.business.controllers.BasicController;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tutors")
public class Tutor extends SimpleModel<Tutor> {

    private String name;

    @OneToOne
    private Student student;

//    private List<Service> services;
    private String exp;
    private Integer age;
    private String place;
    private Boolean isConfirmed;
    private String contact;
    private String email;
    private String education;
    private String shortEducation;
    private String job;
    private String format;

    @Override
    public void update(Tutor another) {
        if (another.name != null) {
            name = another.name;
        }
        if (another.exp != null) {
            exp = another.exp;
        }
        if (another.age != null) {
            age = another.age;
        }
        if (another.place != null) {
            place = another.place;
        }
        if (another.isConfirmed != null) {
            isConfirmed = another.isConfirmed;
        }
        if (another.contact != null) {
            contact = another.contact;
        }
        if (another.email != null) {
            email = another.email;
        }
        if (another.education != null) {
            education = another.education;
        }
        if (another.job != null) {
            job = another.job;
        }
        if (another.format != null) {
            format = another.format;
        }
        if (another.shortEducation != null) {
            shortEducation = another.shortEducation;
        }
    }

    @Override
    public Tutor createShortModel() {
       Tutor tutor = new Tutor();
       tutor.setId(getId());
        return tutor;
    }

    @Override
    public Tutor createDefaultModel(Set<String> expands) {
        Tutor tutor = createShortModel();
        tutor.setAge(getAge());
        tutor.setName(getName());
        tutor.setPlace(getPlace());
        tutor.setEducation(getEducation());
        tutor.setJob(getJob());
        tutor.setFormat(getFormat());
        tutor.setShortEducation(getShortEducation());
//        tutor.services = BasicController.createModelsByExpand(services, "services", expands);
        tutor.student = BasicController.createModelByExpand(student, "student", expands);
        return tutor;
    }

}
