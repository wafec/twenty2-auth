package twenty2.auth.shared.exceptions.api;

import twenty2.core.api.exceptions.BadRequestApiException;

public class ResourceNotFoundApiException extends BadRequestApiException {
    public ResourceNotFoundApiException( String resourceName ) {
        super( String.format( "Resource %s not found", resourceName ) );
    }
}
