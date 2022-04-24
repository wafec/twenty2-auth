package auth.core;

import auth.entities.User;

public interface TokenBuilder {
    String generate( User user );
}
