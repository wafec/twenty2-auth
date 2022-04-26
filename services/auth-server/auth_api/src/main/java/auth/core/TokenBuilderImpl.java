package auth.core;

import auth.entities.User;
import auth.shared.dto.TokenDto;
import auth.shared.exceptions.TokenGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;

@Component
public class TokenBuilderImpl implements TokenBuilder {
    @Autowired
    private ObjectEncryption objectEncryption;

    @Override
    public String generate(User user) throws TokenGenerationException {
        TokenDto tokenDto = new TokenDto();
        try {
            return objectEncryption.encrypt( tokenDto );
        } catch ( GeneralSecurityException exc ) {
            throw new TokenGenerationException();
        }
    }
}
