package twenty2.auth.api.core;

import twenty2.auth.api.exceptions.ObjectHashGeneratorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ObjectHashGeneratorFactoryImpl implements ObjectHashGeneratorFactory {
    private final ObjectMapper objectMapper;
    private final String algorithm;

    public static final String OBJECT_HASH_GENERATOR_ALGORITHM_DEFAULT = "SHA-256";
    public static final String OBJECT_HASH_GENERATOR_ALGORITHM_PROPERTY = "${object-hash-generator-algorithm:" +
            OBJECT_HASH_GENERATOR_ALGORITHM_DEFAULT + "}";

    @Autowired
    public ObjectHashGeneratorFactoryImpl( ObjectMapper objectMapper,
                                           @Value( OBJECT_HASH_GENERATOR_ALGORITHM_PROPERTY ) String algorithm ) {
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
