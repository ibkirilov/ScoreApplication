package webproject.score.services.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import webproject.score.data.models.User;
import webproject.score.services.models.TeamCreateServiceModel;
import webproject.score.services.models.UserServiceModel;

import java.util.List;

public interface UsersService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    User getUserByUsername(String username);

    List<UserServiceModel> getAllUsers();

    void deleteRoleById(String id, String roleString);

    void addRoleById(String id, String role_user);
}
