package auth.shared.core;

import auth.shared.exceptions.ObjectHashParserException;

public interface ObjectHashParserFactory {
    <T> ObjectHashParser<T> build( Class<T> classType, String jsonBase64 ) throws ObjectHashParserException;
}
