package twenty2.auth.shared.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignatureValidatorException extends Exception {
    public SignatureValidatorException( Exception innerException ) {
        super( innerException );
    }
}
