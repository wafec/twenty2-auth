package auth.core;

import auth.exceptions.ObjectHashGeneratorException;

public interface ObjectHashGeneratorFactory {
    ObjectHashGenerator build( Object objInstance ) throws ObjectHashGeneratorException;

    String algorithm();
}
