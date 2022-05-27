package twenty2.auth.api.controllers;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.api.dao.ClaimDao;
import twenty2.auth.api.dao.RoleDao;
import twenty2.auth.api.dao.UserDao;
import twenty2.auth.api.helpers.RoleTransferHelper;
import twenty2.auth.api.helpers.UserTransferHelper;
import twenty2.auth.api.mappers.ClaimMapper;
import twenty2.auth.api.mappers.RoleMapper;
import twenty2.auth.shared.dto.ClaimCreationDto;
import twenty2.auth.shared.dto.RoleCreationDto;
import twenty2.auth.shared.exceptions.ResourceNotFoundException;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class AuthorizationApiImplTest {
    @Mock
    private ClaimDao claimDao;

    @Mock
    private ClaimMapper claimMapper;

    @Mock
    private RoleDao roleDao;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserDao userDao;

    @Mock
    private UserTransferHelper userTransferHelper;

    @Mock
    private RoleTransferHelper roleTransferHelper;

    AuthorizationApiImpl sut;

    @BeforeEach
    void setup() {
        sut = new AuthorizationApiImpl( claimDao, claimMapper, roleDao, roleMapper, entityManager,
                userDao, userTransferHelper, roleTransferHelper );
    }

    @Test
    void whenCreatingClaim() {
        ClaimCreationDto claimCreationDto = ClaimCreationDto.builder().value( "test" ).build();

        sut.createClaim( claimCreationDto );

        verify( claimDao ).save( any() );
        ArgumentCaptor<ClaimCreationDto> claimCreationDtoArgumentCaptor =
                ArgumentCaptor.forClass( ClaimCreationDto.class );
        verify( claimMapper ).map( claimCreationDtoArgumentCaptor.capture() );
        ClaimCreationDto claimCreationDtoArgument = claimCreationDtoArgumentCaptor.getValue();
        assertThat( claimCreationDtoArgument.getValue(), equalTo( "test" ) );
    }

    @Test
    void whenCreatingRole() throws ResourceNotFoundException {
        RoleCreationDto roleCreationDto = RoleCreationDto.builder()
                .name( "test" ).claimIds(Collections.singletonList( 1L )).build();
        when( roleMapper.map( any( RoleCreationDto.class ) ) )
                .then( Answers.RETURNS_DEEP_STUBS );

        sut.createRole( roleCreationDto );

        verify( roleDao ).save( any() );
        verify( roleDao ).addClaimToRole( any(), eq( 1L ) );
        ArgumentCaptor<RoleCreationDto> roleCreationDtoArgumentCaptor =
                ArgumentCaptor.forClass( RoleCreationDto.class );
        verify( roleMapper ).map( roleCreationDtoArgumentCaptor.capture() );
        RoleCreationDto roleCreationDtoArgument = roleCreationDtoArgumentCaptor.getValue();
        assertThat( roleCreationDtoArgument.getName(), equalTo( "test" ) );
    }

    @Test
    void whenAddingClaimToRole() throws ResourceNotFoundApiException {
        List<Long> claimIds = ImmutableList.of( 1L, 2L );

        sut.addClaimToRole( 1L, claimIds );

        verify( roleDao ).addClaimToRole( eq( 1L ), eq( 1L ) );
        verify( roleDao ).addClaimToRole( eq( 1L ), eq( 2L ) );
    }

    @Test
    void whenRemovingClaimFromRole() throws ResourceNotFoundApiException {
        List<Long> claimIds = ImmutableList.of( 1L, 2L );

        sut.removeClaimFromRole( 1L, claimIds );

        verify( roleDao ).removeClaimFromRole( eq( 1L ), eq( 1L ) );
        verify( roleDao ).removeClaimFromRole( eq( 1L ), eq( 2L ) );
    }

    @Test
    void whenAddingClaimToUser() throws ResourceNotFoundApiException {
        List<Long> claimIds = ImmutableList.of( 1L, 2L );

        sut.addClaimToUser( 1L, claimIds );

        verify( userDao ).addClaimToUser( eq( 1L ), eq( 1L ) );
        verify( userDao ).addClaimToUser( eq( 1L ), eq( 2L ) );
    }

    @Test
    void whenRemovingClaimFromUser() throws ResourceNotFoundApiException {
        List<Long> claimIds = ImmutableList.of( 1L, 2L );

        sut.removeClaimFromUser( 1L, claimIds );

        verify( userDao ).removeClaimFromUser( eq( 1L ), eq( 2L ) );
        verify( userDao ).removeClaimFromUser( eq( 1L ), eq( 2L ) );
    }

    @Test
    void whenAddingRoleToUser() throws ResourceNotFoundApiException {
        List<Long> roleIds = ImmutableList.of( 1L, 2L );

        sut.addRoleToUser( 1L, roleIds );

        verify( userDao ).addRoleToUser( eq( 1L ), eq( 2L ) );
        verify( userDao ).addRoleToUser( eq( 1L ), eq( 2L ) );
    }

    @Test
    void whenRemovingRoleFromUser() throws ResourceNotFoundApiException {
        List<Long> roleIds = ImmutableList.of( 1L, 2L );

        sut.removeRoleFromUser( 1L, roleIds );

        verify( userDao ).removeRoleFromUser( eq( 1L ), eq( 1L ) );
        verify( userDao ).removeRoleFromUser( eq( 1L ), eq( 2L ) );
    }
}
