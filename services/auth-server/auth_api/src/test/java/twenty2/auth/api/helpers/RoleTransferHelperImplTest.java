package twenty2.auth.api.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.api.dao.RoleDao;
import twenty2.auth.api.entities.Role;
import twenty2.auth.api.mappers.RoleMapper;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class RoleTransferHelperImplTest {
    @Mock
    private RoleMapper roleMapper;

    @Mock
    private RoleDao roleDao;

    private RoleTransferHelperImpl sut;

    @BeforeEach
    private void setup() {
        sut = new RoleTransferHelperImpl( roleDao, roleMapper );
    }

    @Test
    void whenRetrievingARole_ShouldMapThat() throws Exception {
        Role role = mock( Role.class, Answers.RETURNS_DEEP_STUBS );
        when( roleDao.findById( 1L ) ).thenReturn( Optional.of( role ) );

        sut.getRole( 1L );

        verify( roleMapper ).map( any( Role.class ) );
    }

    @Test
    void whenProvidingNonExistentRoleIdToGetARole_ShouldThrowAnException() {
        when( roleDao.findById( 1L ) ).thenReturn( Optional.empty() );

        assertThrows( ResourceNotFoundApiException.class, () -> sut.getRole( 1L ) );

        verify( roleMapper, never() ).map( any( Role.class ) );
    }
}
