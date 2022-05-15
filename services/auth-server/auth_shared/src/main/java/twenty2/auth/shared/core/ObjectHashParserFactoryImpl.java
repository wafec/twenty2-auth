package twenty2.auth.shared.core;

import twenty2.auth.shared.exceptions.ObjectHashParserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectHashParserFactoryImpl implements ObjectHashParserFactory {
    private final ObjectMapper objectMapper;

    @Autowired
    public ObjectHashParserFactoryImpl( ObjectMapper objectMapper ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> ObjectHashParser<T> build(Class<T> classType, String jsonBase64) throws ObjectHashParserException {
        return new ObjectHashParserImpl<T>( classType, jsonBase64, objectMapper );
    }
}
