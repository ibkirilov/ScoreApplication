package webproject.score.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid info for user registration")
public class NotValidUserRegisterInfoException extends RuntimeException {
    public NotValidUserRegisterInfoException(String message) {
        super(message);
    }
}
