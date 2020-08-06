package webproject.score.services.services.validation.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webproject.score.data.repositories.UserRepository;
import webproject.score.services.models.RegisterUserServiceModel;
import webproject.score.services.services.validation.AuthValidationService;

@Service
public class AuthValidationServiceImpl implements AuthValidationService {
    private final UserRepository userRepository;

    @Autowired
    public AuthValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(RegisterUserServiceModel user) {
        return this.arePasswordsValid(user.getPassword(), user.getConfirmPassword()) &&
                this.isEmailValid(user.getEmail()) &&
                this.isUsernameFree(user.getUsername());
    }

    public boolean isUsernameFree(String username) {
        return !this.userRepository.existsByUsername(username);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean arePasswordsValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
