package webproject.score.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.score.data.models.Role;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel {
    private String id;
    private String username;
    private Set<Role> authorities;
    private String email;

}
