package twenty2.auth.shared.exceptions.api;

import twenty2.core.api.exceptions.BadRequestApiException;

public class AccountNotFoundApiException extends BadRequestApiException {
    public AccountNotFoundApiException() {
        super( "Account not found" );
    }
}
