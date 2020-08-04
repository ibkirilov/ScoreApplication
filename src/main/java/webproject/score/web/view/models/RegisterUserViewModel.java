package webproject.score.web.view.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserViewModel {

    @Length(min = 3, max = 16, message = "Username must be between 3 and 16 characters!")
    private String username;

    @Length(min = 3, max = 16, message = "Password must be between 3 and 16 characters")
    private String password;
    private String confirmPassword;

    @Email(message = "Your email must contains '@' character!")
    private String email;

    @Length(min = 2, max = 24, message = "Your team name must be between 2 and 24 characters!")
    private String teamName;

    @Length(min = 3, max = 16, message = "Your town name must be between 3 and 16 characters!")
    private String town;

    @Length(min = 3, max = 16, message = "Your stadium name must be between 3 and 16 characters!")
    private String stadiumName;

    @Length(min = 3, max = 16, message = "Your fan club name must be between 3 and 16 characters!")
    private String fanClub;

    private MultipartFile logoLink;
}
