package auth.api;

import auth.core.TokenBuilder;
import auth.dao.UserDao;
import auth.entities.User;
import auth.shared.api.AuthenticationApi;
import auth.shared.dto.UserAndPasswordDto;
import auth.shared.exceptions.TokenGenerationException;
import auth.shared.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/authentication" )
public class AuthenticationApiImpl implements AuthenticationApi {
    private final UserDao userDao;
    private final TokenBuilder tokenBuilder;

    @Autowired
    public AuthenticationApiImpl(
            UserDao userDao,
            TokenBuilder tokenBuilder
    ) {
        this.userDao = userDao;
        this.tokenBuilder = tokenBuilder;
    }

    @GetMapping( "/authenticate" )
    @Override
    public String authenticate( UserAndPasswordDto userAndPassword )
            throws UserNotFoundException, TokenGenerationException {
        User user = userDao.findByNameAndPassword( userAndPassword.getUser(), userAndPassword.getPassword() );

        if ( user == null ) {
            throw new UserNotFoundException();
        }

        return tokenBuilder.generate( user );
    }
}
