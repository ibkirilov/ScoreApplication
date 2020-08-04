package webproject.score.web.view.models;

import lombok.Data;

import java.util.Set;

@Data
public class UserViewModel {
    private String id;
    private String username;
    private Set<String> authorities;
    private String email;
}
