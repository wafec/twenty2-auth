package twenty2.auth.shared.core;

import twenty2.auth.shared.exceptions.ObjectHashParserException;

public interface ObjectHashParserFactory {
    <T> ObjectHashParser<T> build( Class<T> classType, String jsonBase64 ) throws ObjectHashParserException;
}
