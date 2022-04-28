package auth.api;

import auth.core.TokenBuilder;
import auth.dao.AuthorizationDao;
import auth.dao.UserDao;
import auth.entities.Authorization;
import auth.entities.User;
import auth.shared.api.AuthenticationApi;
import auth.shared.dto.AuthorizationDto;
import auth.shared.dto.UserAndPasswordDto;
import auth.shared.exceptions.TokenGenerationException;
import auth.shared.exceptions.UserNotFoundException;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping( "/authentication" )
public class AuthenticationApiImpl implements AuthenticationApi {
    private final UserDao userDao;
    private final TokenBuilder tokenBuilder;
    private final AuthorizationDao authorizationDao;

    @Autowired
    public AuthenticationApiImpl(
            UserDao userDao,
            TokenBuilder tokenBuilder,
            AuthorizationDao authorizationDao
    ) {
        this.userDao = userDao;
        this.tokenBuilder = tokenBuilder;
        this.authorizationDao = authorizationDao;
    }

    @GetMapping( "/authenticate" )
    @Override
    public AuthorizationDto authenticate( UserAndPasswordDto userAndPassword )
            throws UserNotFoundException, TokenGenerationException {
        User user = userDao.findByNameAndPassword( userAndPassword.getUser(), userAndPassword.getPassword() );

        if ( user == null ) {
            throw new UserNotFoundException();
        }

        Authorization authorization = new Authorization();
        authorization.setUser( user );
        authorization.setCreatedDate( new Date() );
        authorization.setExpiresIn( new DateTime().plusDays( 1 ).toDate() );
        authorization.setToken( tokenBuilder.generate( user ) );

        authorizationDao.save( authorization );

        AuthorizationDto authorizationDto = new AuthorizationDto();
        BeanUtils.copyProperties( authorization, authorizationDto );
        return authorizationDto;
    }
}
