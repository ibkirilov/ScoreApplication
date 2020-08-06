package webproject.score.services.services;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import webproject.score.base.TestBase;
import webproject.score.data.models.Role;
import webproject.score.data.repositories.RoleRepository;

import static org.junit.Assert.*;

public class RolesServiceTests extends TestBase {
    private String adminRole = "ADMIN_ROLE";
    private String userRole = "USER_ROLE";

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    RolesService rolesService;

    @Test
    public void getRole_shouldReturnCorrectRole() {
        Role admin = new Role();
        admin.setAuthority(adminRole);
        Mockito.when(this.roleRepository.findByAuthority(adminRole)).thenReturn(java.util.Optional.of(admin));

        Role user = new Role();
        user.setAuthority(userRole);
        Mockito.when(this.roleRepository.findByAuthority(userRole)).thenReturn(java.util.Optional.of(user));

        assertEquals(admin, this.rolesService.getRole(adminRole));
        assertEquals(user, this.rolesService.getRole(userRole));
    }

}
