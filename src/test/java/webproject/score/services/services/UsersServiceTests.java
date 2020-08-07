package webproject.score.services.services;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import webproject.score.base.TestBase;
import webproject.score.data.models.Role;
import webproject.score.data.models.User;
import webproject.score.data.repositories.UserRepository;
import webproject.score.errors.CannotDeleteLastAdministratorException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class UsersServiceTests extends TestBase {

    @MockBean
    UserRepository userRepository;

    @MockBean
    RolesService rolesService;

    @Autowired
    UsersService usersService;

    @Test
    public void loadUserByUsername_shouldReturnUserDetailsWithSameUsername() {
        User user = new User();
        user.setUsername("Pesho");
        user.setPassword("Kapan");
        Set<Role> roles = new HashSet<>();
        user.setAuthorities(roles);

        Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);

        UserDetails userDetails = this.usersService.loadUserByUsername("Pesho");

        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    public void deleteRoleById_shouldThrowCannotDeleteLastAdministratorException() {
        User user = new User();
        user.setId("adminId");
        Role adminRole = new Role();
        adminRole.setAuthority("ROLE_ADMIN");
        adminRole.setId("admin");
        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");
        userRole.setId("user");
        user.setAuthorities(Set.of(adminRole, userRole));

        Mockito.when(this.userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(user));
        Mockito.when(this.rolesService.getRole(any(String.class))).thenReturn(adminRole);
        Mockito.when(this.userRepository.findAll()).thenReturn(List.of(user));

        assertThrows(CannotDeleteLastAdministratorException.class, () -> this.usersService.deleteRoleById("adminId", "ROLE_ADMIN"));
    }

    @Test
    public void deleteRoleById_shouldRemoveRightRole() {

        Role adminRole = new Role();
        adminRole.setAuthority("ROLE_ADMIN");
        adminRole.setId("admin");
        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");
        userRole.setId("user");

        Set<Role> rolesOne = new HashSet<>();
        rolesOne.add(adminRole);
        rolesOne.add(userRole);

        User admin = new User();
        admin.setId("adminId");
        admin.setAuthorities(rolesOne);

        Set<Role> rolesTwo = new HashSet<>();
        rolesTwo.add(adminRole);
        rolesTwo.add(userRole);

        User adminTwo = new User();
        adminTwo.setId("secondAdminId");
        adminTwo.setAuthorities(rolesTwo);

        Mockito.when(this.userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(admin));
        Mockito.when(this.rolesService.getRole(any(String.class))).thenReturn(adminRole);
        Mockito.when(this.userRepository.findAll()).thenReturn(List.of(admin, adminTwo));

        this.usersService.deleteRoleById("adminId", "ROLE_ADMIN");

        assertFalse(admin.getAuthorities().contains(adminRole));
        assertTrue(adminTwo.getAuthorities().contains(adminRole));
    }

    @Test
    public void addRoleById_shouldAddRoleToUser() {
        Role adminRole = new Role();
        adminRole.setAuthority("ROLE_ADMIN");
        adminRole.setId("admin");

        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");
        userRole.setId("user");

        Set<Role> rolesOne = new HashSet<>();
        rolesOne.add(adminRole);

        User admin = new User();
        admin.setId("adminId");
        admin.setAuthorities(rolesOne);

        Mockito.when(this.userRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(admin));
        Mockito.when(this.rolesService.getRole(any(String.class))).thenReturn(userRole);

        this.usersService.addRoleById("adminId", "ROLE_USER");
        assertEquals(2, admin.getAuthorities().size());
    }

    @Test
    public void addRoleById_shouldThrowNullPointerException() {
        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");
        userRole.setId("user");

        Mockito.when(this.userRepository.findById(any(String.class))).thenReturn(null);
        Mockito.when(this.rolesService.getRole(any(String.class))).thenReturn(userRole);

        assertThrows(NullPointerException.class, () -> this.usersService.addRoleById("userId", "ROLE_USER"));
    }
}
