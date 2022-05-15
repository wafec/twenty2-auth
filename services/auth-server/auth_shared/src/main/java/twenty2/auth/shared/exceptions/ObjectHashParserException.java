package twenty2.auth.shared.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObjectHashParserException extends Exception {
    public ObjectHashParserException( Exception innerException ) {
        super( innerException );
    }
}
