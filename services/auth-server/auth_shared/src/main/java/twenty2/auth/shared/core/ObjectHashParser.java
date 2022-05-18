package twenty2.auth.shared.core;

import twenty2.auth.shared.exceptions.ObjectHashParserException;

public interface ObjectHashParser <T> {
    T getInstance();
    byte[] hash( String algorithm ) throws ObjectHashParserException;
}
