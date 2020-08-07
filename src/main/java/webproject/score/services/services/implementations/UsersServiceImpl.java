package webproject.score.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import webproject.score.data.models.Role;
import webproject.score.data.models.User;
import webproject.score.data.repositories.UserRepository;
import webproject.score.errors.CannotDeleteLastAdministratorException;
import webproject.score.services.models.UserServiceModel;
import webproject.score.services.services.RolesService;
import webproject.score.services.services.UsersService;
import webproject.score.web.view.models.UserViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RolesService rolesService;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RolesService rolesService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.rolesService = rolesService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserServiceModel> getAllUsers() {

        return this.userRepository.findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRoleById(String id, String roleString) {

        User user = this.userRepository.findById(id).orElse(null);
        Role role = this.rolesService.getRole(roleString);

        List<UserViewModel> admins = this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserViewModel.class))
                .filter(u -> u.getAuthorities().contains("ROLE_ADMIN"))
                .collect(Collectors.toList());

        if (admins.size() == 1 && roleString.equals("ROLE_ADMIN")) {
            throw new CannotDeleteLastAdministratorException("Cannot delete last administrator!");
        }

        if (user != null) {
            Set<Role> roles = user.getAuthorities();
            roles.remove(role);
            user.setAuthorities(roles);
            this.userRepository.save(user);
        }
    }

    @Override
    public void addRoleById(String id, String roleString) {
        User user = this.userRepository.findById(id).orElse(null);
        Role role = this.rolesService.getRole(roleString);

        if (user == null) {
            throw new NullPointerException("There is no such user!");
        }
        Set<Role> roles = user.getAuthorities();
        roles.add(role);
        user.setAuthorities(roles);
        this.userRepository.save(user);
    }
}
