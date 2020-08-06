package webproject.score.services.services;

import webproject.score.data.models.User;
import webproject.score.services.models.RegisterUserServiceModel;

public interface AuthService {
    User register(RegisterUserServiceModel model);
}
