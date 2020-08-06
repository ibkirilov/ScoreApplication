package webproject.score.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.score.data.models.Role;
import webproject.score.data.models.User;
import webproject.score.data.repositories.UserRepository;
import webproject.score.errors.NotValidUserRegisterInfoException;
import webproject.score.services.models.RegisterUserServiceModel;
import webproject.score.services.services.AuthService;
import webproject.score.services.services.HashingService;
import webproject.score.services.services.RolesService;
import webproject.score.services.services.validation.AuthValidationService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final ModelMapper mapper;
    private final HashingService hashingService;
    private final AuthValidationService authValidationService;
    private final UserRepository userRepository;
    private final RolesService rolesService;

    @Autowired
    public AuthServiceImpl(ModelMapper mapper, HashingService hashingService, AuthValidationService authValidationService, UserRepository userRepository, RolesService rolesService) {
        this.mapper = mapper;
        this.hashingService = hashingService;
        this.authValidationService = authValidationService;
        this.userRepository = userRepository;
        this.rolesService = rolesService;
    }

    @Override
    @Transactional
    public User register(RegisterUserServiceModel model) {
        if(!authValidationService.isValid(model)) {
            throw new NotValidUserRegisterInfoException("Invalid user registration info!");
        }

        User user = mapper.map(model, User.class);
        user.setPassword(hashingService.hash(model.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        Set<Role> authorities = new HashSet<>();
        Role role;
        if (userRepository.count() == 0) {
            role = rolesService.getRole("ROLE_ADMIN");
            authorities.add(role);
        }
        role = rolesService.getRole("ROLE_USER");
        authorities.add(role);

        user.setAuthorities(authorities);        

        return userRepository.save(user);
    }
}
