package webproject.score.services.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.repositories.UserRepository;
import webproject.score.services.models.RegisterUserServiceModel;
import webproject.score.services.services.validation.AuthValidationService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AuthValidationServiceTests extends TestBase {
    @MockBean
    UserRepository userRepository;

    @Autowired
    AuthValidationService authValidationService;

    @Test
    public void isValid_checkIfGivenUserWithCorrectDataIsValid_shouldReturnTrue() {
        String username = "someUsername";
        RegisterUserServiceModel user = new RegisterUserServiceModel();
        user.setPassword("password");
        user.setConfirmPassword("password");
        user.setEmail("ivo@abv.bg");
        user.setUsername(username);

        Mockito.when(!this.userRepository.existsByUsername(username)).thenReturn(false);

        assertTrue(authValidationService.isValid(user));
    }

    @Test
    public void isValid_checkIfGivenUserWithDifferentPasswordAndConfirmPassword_shouldReturnFalse() {
        String username = "someUsername";
        RegisterUserServiceModel user = new RegisterUserServiceModel();
        user.setPassword("password1");
        user.setConfirmPassword("password2");
        user.setEmail("ivo@abv.bg");
        user.setUsername(username);

        Mockito.when(!this.userRepository.existsByUsername(username)).thenReturn(false);

        assertFalse(authValidationService.isValid(user));
    }

    @Test
    public void isValid_checkIfGivenUserWithWrongEmail_shouldReturnFalse() {
        String username = "someUsername";
        RegisterUserServiceModel user = new RegisterUserServiceModel();
        user.setPassword("password");
        user.setConfirmPassword("password");
        user.setEmail("ivoATabv.bg");
        user.setUsername(username);

        Mockito.when(!this.userRepository.existsByUsername(username)).thenReturn(false);

        assertFalse(authValidationService.isValid(user));
    }

    @Test
    public void isValid_checkIfUserExistsInRepository_shouldReturnFalse() {
        String username = "someUsername";
        RegisterUserServiceModel user = new RegisterUserServiceModel();
        user.setPassword("password");
        user.setConfirmPassword("password");
        user.setEmail("ivo@abv.bg");
        user.setUsername(username);

        Mockito.when(!this.userRepository.existsByUsername(username)).thenReturn(true);

        assertFalse(authValidationService.isValid(user));
    }
}
