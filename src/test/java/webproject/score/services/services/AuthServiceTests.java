package webproject.score.services.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.models.Role;
import webproject.score.data.models.User;
import webproject.score.data.repositories.UserRepository;
import webproject.score.errors.NotValidUserRegisterInfoException;
import webproject.score.services.models.RegisterUserServiceModel;


import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class AuthServiceTests extends TestBase {
    RegisterUserServiceModel user = new RegisterUserServiceModel();

    @Autowired
    AuthService authService;

    @Autowired
    HashingService hashingService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void initUser() {
        user.setUsername("Test username");
        user.setEmail("ivo@abv.bg");
        user.setPassword("testPassword");
        user.setConfirmPassword("testPassword");
    }

    @Test
    public void register_ifNotValidUserIsGiven_shouldThrowAnException() {

        user.setConfirmPassword("wrongPassword");

        Mockito.when(!this.userRepository.existsByUsername("Test username")).thenReturn(false);

        assertThrows(NotValidUserRegisterInfoException.class, () -> authService.register(user));
    }

    @Test
    public void register_ifValidUserIsGiven_shouldSaveToDB() {

        Mockito.when(!this.userRepository.existsByUsername("Test username")).thenReturn(false);

        User realUser = new User();
        realUser.setPassword(hashingService.hash(user.getPassword()));
        realUser.setAccountNonExpired(true);
        realUser.setAccountNonLocked(true);
        realUser.setCredentialsNonExpired(true);
        realUser.setEnabled(true);
        Set<Role> authorities = new HashSet<>();
        Role role = new Role();
        role.setAuthority("ROLE_ADMIN");
        authorities.add(role);
        Role role2 = new Role();
        role2.setAuthority("ROLE_USER");
        authorities.add(role2);
        authorities.add(role2);
        realUser.setAuthorities(authorities);

        Mockito.when(this.userRepository.save(any(User.class))).thenReturn(realUser);

        User registeredUser = this.authService.register(user);

        assertEquals(registeredUser, realUser);
    }
}
