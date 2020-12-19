package org.acme.business.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends SimpleModel<Role> {

    public enum RoleName {

        PUBLIC(false),
        PUBLIC_VIP(true),
        STUDENT(false),
        TUTOR(false),
        ADMIN(true),
        ORGANIZER(false);

        public boolean isVIP;

        RoleName(boolean isVIP) {
            this.isVIP = isVIP;
        }

    }

    @NotNull(message = "validation.role.name.required")
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Override
    public void update(Role simpleModel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role createShortModel() {
        Role role = new Role();
        role.setId(getId());
        role.name = name;
        return role;
    }

    @Override
    public Role createDefaultModel(Set<String> expands) {
        return createShortModel();
    }

    @Override
    public String toString() {
        return String.format("%s:%s", getId(), name);
    }

}
