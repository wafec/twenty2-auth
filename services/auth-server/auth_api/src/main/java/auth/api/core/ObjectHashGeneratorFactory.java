package auth.api.core;

import auth.api.exceptions.ObjectHashGeneratorException;

public interface ObjectHashGeneratorFactory {
    ObjectHashGenerator build( Object objInstance ) throws ObjectHashGeneratorException;

    String algorithm();
}
