package webproject.score.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "All groups are full")
public class AllGroupsAreFullException extends RuntimeException {
    public AllGroupsAreFullException(String message) {
        super(message);
    }
}
