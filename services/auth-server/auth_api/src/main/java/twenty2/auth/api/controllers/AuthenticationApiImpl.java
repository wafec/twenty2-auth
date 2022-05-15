package twenty2.auth.api.controllers;

import twenty2.auth.api.core.TokenBuilder;
import twenty2.auth.api.dao.UserDao;
import twenty2.auth.api.entities.User;
import twenty2.auth.shared.api.AuthenticationApi;
import twenty2.auth.shared.dto.UserAndPasswordDto;
import twenty2.auth.shared.exceptions.TokenGenerationException;
import twenty2.auth.shared.exceptions.UserNotFoundException;
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
