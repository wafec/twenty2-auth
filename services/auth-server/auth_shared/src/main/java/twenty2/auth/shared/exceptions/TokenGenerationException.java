package twenty2.auth.shared.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenGenerationException extends Exception {
    public TokenGenerationException( Exception innerException ) {
        super( innerException );
    }
}
