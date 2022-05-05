package auth.shared.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenParserException extends Exception {
    public TokenParserException( Exception innerException ) {
        super( innerException );
    }
}
