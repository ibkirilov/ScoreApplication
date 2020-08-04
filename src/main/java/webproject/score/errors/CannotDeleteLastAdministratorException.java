package webproject.score.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "You cannot delete the last administrator!")
public class CannotDeleteLastAdministratorException extends RuntimeException {
    public CannotDeleteLastAdministratorException(String message) {
        super(message);
    }
}
