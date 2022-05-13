package auth.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler( ApiException.class )
    protected ResponseEntity<Object> handleApi( ApiException ex, WebRequest request ) {
        return handleExceptionInternal( ex, ex.getBody(), new HttpHeaders(), ex.getStatus(), request );
    }
}
