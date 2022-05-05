package auth.shared.core;

import auth.shared.exceptions.ObjectHashParserException;

public interface ObjectHashParser <T> {
    T getInstance();
    byte[] hash( String algorithm ) throws ObjectHashParserException;
}
