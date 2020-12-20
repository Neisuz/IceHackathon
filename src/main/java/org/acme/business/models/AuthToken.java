package org.acme.business.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.acme.business.controllers.BasicController;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class AuthToken extends SimpleModel<AuthToken>{

    private Long id;

    @JsonProperty("auth_token")
    private String name;

    private String checksum;

    @JsonProperty("isActive")
    private boolean isActive;

    private Date expiresOn;

    private Student student;

    private Role.RoleName roleName;

//    public Role.RoleName getRoleName() {
//        return student != null && student.getRole() != null ? student.getRole().getName() : null;
//    }

    @Override
    public void update(AuthToken another) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", student=" + student +
                ", checksum='" + checksum + '\'' +
                '}';
    }

    @Override
    public AuthToken createShortModel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AuthToken createDefaultModel(Set<String> expands) {
        AuthToken authToken = new AuthToken();
        authToken.name = name;
        authToken.checksum = checksum;
        authToken.isActive = isActive;
        authToken.expiresOn = expiresOn;
        authToken.student = BasicController.createModelByExpand(student, "student", expands);
        return authToken;
    }

}
