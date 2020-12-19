package org.acme.business.models;

import lombok.Getter;
import lombok.Setter;
import org.acme.business.controllers.BasicController;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "medals")
public class Medal extends SimpleModel<Medal> {

    private String name;
//    private List<Student> students;

    @Override
    public void update(Medal another) {
        if (another.name != null) {
            name = another.name;
        }
    }

    @Override
    public Medal createShortModel() {
        Medal medal = new Medal();
        medal.setId(getId());
        return medal;
    }

    @Override
    public Medal createDefaultModel(Set<String> expands) {
        Medal medal = createShortModel();
        medal.setName(getName());
//        medal.students = BasicController.createModelsByExpand(students, "students", expands);
        return medal;
    }
}
