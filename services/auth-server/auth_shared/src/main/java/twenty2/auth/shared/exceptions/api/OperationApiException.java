package twenty2.auth.shared.exceptions.api;

import twenty2.core.api.exceptions.BadRequestApiException;

public class OperationApiException extends BadRequestApiException {
    public OperationApiException( String message ) {
        super( message );
    }
}
