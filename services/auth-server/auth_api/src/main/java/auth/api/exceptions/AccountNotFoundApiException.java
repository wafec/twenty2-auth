package auth.api.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotFoundApiException extends ApiException {
    @Override
    public Object getBody() {
        return new ApiExceptionBody( "Account not found", getStatus().value() );
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
