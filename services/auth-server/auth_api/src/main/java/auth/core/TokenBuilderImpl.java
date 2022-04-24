package auth.core;

import auth.entities.User;
import org.springframework.stereotype.Component;

@Component
public class TokenBuilderImpl implements TokenBuilder {
    @Override
    public String generate(User user) {
        return null;
    }
}
