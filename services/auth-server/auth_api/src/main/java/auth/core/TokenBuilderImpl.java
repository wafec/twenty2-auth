package auth.core;

import auth.entities.Claim;
import auth.entities.User;
import auth.shared.dto.TokenDto;
import auth.shared.exceptions.TokenGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TokenBuilderImpl implements TokenBuilder {
    private final ObjectEncryption objectEncryption;

    @Autowired
    public TokenBuilderImpl( ObjectEncryption objectEncryption ) {
        this.objectEncryption = objectEncryption;
    }

    @Override
    public String generate(User user) throws TokenGenerationException {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setUser( user.getName() );
        tokenDto.setClaims(
                Optional.ofNullable(user.getClaims())
                        .stream()
                        .flatMap( Collection::stream )
                        .map( Claim::getDescription )
                        .collect( Collectors.toList() )
        );
        try {
            return objectEncryption.encrypt( tokenDto );
        } catch ( GeneralSecurityException exc ) {
            throw new TokenGenerationException();
        }
    }
}
