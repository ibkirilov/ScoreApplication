package webproject.score.services.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webproject.score.data.models.Role;
import webproject.score.data.repositories.RoleRepository;
import webproject.score.services.services.RolesService;

import javax.annotation.PostConstruct;

@Service
public class RolesServiceImpl implements RolesService {
    private final RoleRepository roleRepository;

    @Autowired
    public RolesServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initRoles() {
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setAuthority("ROLE_ADMIN");
            this.roleRepository.saveAndFlush(admin);

            Role user = new Role();
            user.setAuthority("ROLE_USER");
            this.roleRepository.saveAndFlush(user);
        }
    }

    @Override
    public Role getRole(String role) {
        return this.roleRepository.findByAuthority(role).orElse(null);
    }
}
