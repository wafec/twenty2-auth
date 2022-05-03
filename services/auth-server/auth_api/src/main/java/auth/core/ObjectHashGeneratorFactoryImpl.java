package auth.core;

import auth.exceptions.ObjectHashGeneratorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ObjectHashGeneratorFactoryImpl implements ObjectHashGeneratorFactory {
    private final ObjectMapper objectMapper;
    private final String algorithm;

    @Autowired
    public ObjectHashGeneratorFactoryImpl( ObjectMapper objectMapper,
                                           @Value( "${object-hash-generator-algorithm:$null}" ) String algorithm ) {
        this.objectMapper = objectMapper;
        this.algorithm = algorithm;
    }

    @Override
    public ObjectHashGenerator build( Object objInstance ) throws ObjectHashGeneratorException {
        return new ObjectHashGeneratorImpl( objInstance, objectMapper, algorithm );
    }

    @Override
    public String algorithm() {
        return algorithm;
    }
}
