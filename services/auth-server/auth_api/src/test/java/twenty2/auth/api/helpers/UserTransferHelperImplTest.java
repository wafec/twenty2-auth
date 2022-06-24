package twenty2.auth.api.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.api.dao.UserDao;
import twenty2.auth.api.entities.User;
import twenty2.auth.api.mappers.UserMapper;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class UserTransferHelperImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    private UserTransferHelperImpl sut;

    @BeforeEach
    private void setup() {
        sut = new UserTransferHelperImpl( userDao, userMapper );
    }

    @Test
    void whenRetrievingExistentUser_ShouldMapThat() throws Exception {
        User user = mock( User.class, Answers.RETURNS_DEEP_STUBS );
        when( userDao.findById( 1L ) ).thenReturn( Optional.of( user ) );

        sut.getUser( 1L );

        verify( userMapper ).map( any( User.class ) );
    }

    @Test
    void whenProvidingNonExistentUserId_ShouldThrowAnException() {
        when( userDao.findById( 1L ) ).thenReturn( Optional.empty() );

        assertThrows( ResourceNotFoundApiException.class, () -> sut.getUser( 1L ) );

        verify( userMapper, never() ).map( any( User.class ) );
    }
}
