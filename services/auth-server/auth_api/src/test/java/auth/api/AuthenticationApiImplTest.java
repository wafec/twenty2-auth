package auth.api;

import auth.core.TokenBuilder;
import auth.dao.AuthorizationDao;
import auth.dao.UserDao;
import auth.entities.User;
import auth.shared.dto.AuthorizationDto;
import auth.shared.dto.UserAndPasswordDto;
import auth.shared.exceptions.UserNotFoundException;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class AuthenticationApiImplTest {
    @Mock
    private UserDao userDao;
    @Mock
    private TokenBuilder tokenBuilder;
    @Mock
    private AuthorizationDao authorizationDao;
    @Mock
    private Clock clock;

    private AuthenticationApiImpl getSut() {
        return new AuthenticationApiImpl(
                userDao,
                tokenBuilder,
                authorizationDao,
                clock
        );
    }

    @Test
    public void authenticate_ValidUser_ReturnAuthorization() throws Exception {
        // Setup
        AuthenticationApiImpl sut = getSut();
        User user = new User( 1L, "test", "Password123", null );
        Date date = new Date();
        Instant instant = date.toInstant();
        when( userDao.findByNameAndPassword( "test", "Password123" ) )
                .thenReturn( user );
        when( tokenBuilder.generate( user ) )
                .thenReturn( "token_test" );
        when( clock.instant() )
                .thenReturn( instant );

        // Act
        AuthorizationDto actualResult =
                sut.authenticate( new UserAndPasswordDto( "test", "Password123" ) );

        // Assert
        assertThat( actualResult.getToken(), equalTo( "token_test" ) );
        assertThat( actualResult.getCreatedDate(), equalTo( date ) );
        assertThat(
                actualResult.getExpiresIn(),
                equalTo( new DateTime( date ).plusSeconds( AuthenticationApiImpl.DEFAULT_EXPIRES_IN_SECS ).toDate() )
        );
    }

    @Test
    public void authenticate_UserNotFound_ThrowException() {
        // Setup
        final AuthenticationApiImpl sut = getSut();
        when( userDao.findByNameAndPassword( "test", "Password123" ) )
                .thenReturn( null );

        // Act
        // Assert
        assertThrows( UserNotFoundException.class, () -> {
            sut.authenticate( new UserAndPasswordDto( "test", "Password123" ) );
        } );
    }
}
