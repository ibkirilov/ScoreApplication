package webproject.score.services.services;

import webproject.score.data.models.Role;

public interface RolesService {
    void initRoles();

    Role getRole(String admin);
}
