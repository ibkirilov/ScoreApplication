package webproject.score.services.services.validation;

import webproject.score.services.models.RegisterUserServiceModel;

public interface AuthValidationService {
    boolean isValid(RegisterUserServiceModel user);
}
