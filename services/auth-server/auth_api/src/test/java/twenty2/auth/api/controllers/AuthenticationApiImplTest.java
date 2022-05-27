package twenty2.auth.api.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.api.core.TokenBuilder;
import twenty2.auth.api.dao.ClaimDao;
import twenty2.auth.api.dao.UserDao;
import twenty2.auth.shared.dto.UserAndPasswordDto;
import twenty2.auth.shared.exceptions.TokenGenerationException;
import twenty2.auth.shared.exceptions.UserNotFoundException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class AuthenticationApiImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private TokenBuilder tokenBuilder;

    @Mock
    private ClaimDao claimDao;

    private AuthenticationApiImpl sut;

    @BeforeEach
    void setup() {
        sut = new AuthenticationApiImpl( userDao, tokenBuilder, claimDao );
    }

    @Test
    void whenAuthenticatingUserNameAndPassword_WithValidCredentials()
            throws UserNotFoundException, TokenGenerationException {
        when( userDao.findByNameAndPassword( any(), any() ) )
                .then( Answers.RETURNS_DEEP_STUBS );
        UserAndPasswordDto userAndPasswordDto = UserAndPasswordDto.builder()
                .user( "testUser" )
                .password( "testPassword" )
                .build();

        sut.authenticate( userAndPasswordDto );

        verify( userDao ).findByNameAndPassword( eq( "testUser" ), eq( "testPassword" ) );
    }

    @Test
    void whenAuthenticatingUserNameAndPassword_WithInvalidCredentials() {
        when( userDao.findByNameAndPassword( any(), any() ) )
                .thenReturn( null );
        UserAndPasswordDto userAndPasswordDto = UserAndPasswordDto.builder().build();

        Assertions.assertThrows( UserNotFoundException.class, () -> sut.authenticate( userAndPasswordDto ) );
    }
}
