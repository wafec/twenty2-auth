package auth.api.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {
    public abstract Object getBody();
    public abstract HttpStatus getStatus();
}
