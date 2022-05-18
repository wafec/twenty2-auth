package twenty2.auth.api.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObjectHashGeneratorException extends Exception {
    public ObjectHashGeneratorException( Exception innerException ) {
        super( innerException );
    }
}
