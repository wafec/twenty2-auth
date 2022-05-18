package twenty2.auth.api.core;

import twenty2.auth.api.exceptions.ObjectHashGeneratorException;

public interface ObjectHashGeneratorFactory {
    ObjectHashGenerator build( Object objInstance ) throws ObjectHashGeneratorException;

    String algorithm();
}
