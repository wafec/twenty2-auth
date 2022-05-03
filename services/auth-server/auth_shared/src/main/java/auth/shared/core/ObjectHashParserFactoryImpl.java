package auth.shared.core;

import auth.shared.exceptions.ObjectHashParserException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectHashParserFactoryImpl implements ObjectHashParserFactory {
    private final ObjectMapper objectMapper;

    public ObjectHashParserFactoryImpl( ObjectMapper objectMapper ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> ObjectHashParser<T> build(Class<T> classType, String jsonBase64) throws ObjectHashParserException {
        return new ObjectHashParserImpl<T>( classType, jsonBase64, objectMapper );
    }
}
