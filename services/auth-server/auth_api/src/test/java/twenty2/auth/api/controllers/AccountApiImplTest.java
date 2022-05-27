package twenty2.auth.api.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.api.dao.AccountDao;
import twenty2.auth.api.dao.UserDao;
import twenty2.auth.api.entities.Account;
import twenty2.auth.api.entities.User;
import twenty2.auth.api.mappers.AccountMapper;
import twenty2.auth.api.mappers.UserMapper;
import twenty2.auth.shared.dto.AccountCreationDto;
import twenty2.auth.shared.dto.PersonalInfoDto;
import twenty2.auth.shared.dto.UserSignUpDto;
import twenty2.auth.shared.exceptions.api.AccountNotFoundApiException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class AccountApiImplTest {
    @Mock
    private AccountDao accountDao;

    @Mock
    private UserDao userDao;

    @Mock( answer = Answers.RETURNS_DEEP_STUBS )
    private AccountMapper accountMapper;

    @Mock( answer = Answers.RETURNS_DEEP_STUBS )
    private UserMapper userMapper;

    private AccountApiImpl sut;

    @BeforeEach
    void setup() {
        sut = new AccountApiImpl( accountDao, userDao, accountMapper, userMapper );
    }

    @Test
    void whenCreatingUser() {
        UserSignUpDto userSignUpDto = UserSignUpDto.builder().name( "test" ).build();
        PersonalInfoDto personalInfoDto = PersonalInfoDto.builder().firstName( "testFirstName" ).build();
        AccountCreationDto accountCreationDto = AccountCreationDto.builder()
                .user( userSignUpDto )
                .personalInfo( personalInfoDto )
                .build();

        sut.create( accountCreationDto );

        verify( userDao ).save( any() );
        verify( accountDao ).save( any() );
        ArgumentCaptor<UserSignUpDto> userSignUpDtoArgumentCaptor = ArgumentCaptor.forClass( UserSignUpDto.class );
        verify( userMapper ).map( userSignUpDtoArgumentCaptor.capture() );
        UserSignUpDto userSignUpDtoArgument = userSignUpDtoArgumentCaptor.getValue();
        assertThat( userSignUpDtoArgument.getName(), equalTo( "test" ) );
        ArgumentCaptor<PersonalInfoDto> personalInfoDtoArgumentCaptor =
                ArgumentCaptor.forClass( PersonalInfoDto.class );
        verify( accountMapper ).map( personalInfoDtoArgumentCaptor.capture() );
        PersonalInfoDto personalInfoDtoArgument = personalInfoDtoArgumentCaptor.getValue();
        assertThat( personalInfoDtoArgument.getFirstName(), equalTo( "testFirstName" ) );
    }

    @Test
    void whenUpdatingAccount() throws AccountNotFoundApiException {
        Account account = Account.builder().id( 1L ).firstName( "testFirstName" ).build();
        when( accountDao.findById( any() ) )
                .thenReturn( Optional.of( account ) );
        PersonalInfoDto personalInfoDto = PersonalInfoDto.builder().firstName( "testFirstName_Changed" ).build();

        sut.updateAccount( 1L, personalInfoDto );

        verify( accountDao ).findById( eq( 1L ) );
        verify( accountDao ).save( eq( account ) );
        ArgumentCaptor<PersonalInfoDto> personalInfoDtoArgumentCaptor =
                ArgumentCaptor.forClass( PersonalInfoDto.class );
        verify( accountMapper ).map( personalInfoDtoArgumentCaptor.capture(), eq( account ) );
        PersonalInfoDto personalInfoDtoArgument = personalInfoDtoArgumentCaptor.getValue();
        assertThat( personalInfoDtoArgument.getFirstName(), equalTo( "testFirstName_Changed" ) );
    }

    @Test
    void whenUpdatingPassword() throws AccountNotFoundApiException {
        User user = User.builder().build();
        Account account = Account.builder().id( 1L ).user( user ).build();
        when( accountDao.findById( any() ) )
                .thenReturn( Optional.of( account ) );
        UserSignUpDto userSignUpDto = UserSignUpDto.builder().password( "testPassword_Changed" ).build();

        sut.updatePassword( 1L, userSignUpDto );

        verify( accountDao ).findById( 1L );
        ArgumentCaptor<UserSignUpDto> userSignUpDtoArgumentCaptor = ArgumentCaptor.forClass( UserSignUpDto.class );
        verify( userMapper ).map( userSignUpDtoArgumentCaptor.capture(), eq( user ) );
        UserSignUpDto userSignUpDtoArgument = userSignUpDtoArgumentCaptor.getValue();
        assertThat( userSignUpDtoArgument.getPassword(), equalTo( "testPassword_Changed" ) );
        verify( userDao ).save( eq( user ) );
    }

    @Test
    void whenUpdatingPassword_WithInvalidUser() {
        User user = User.builder().name( "testUser1" ).build();
        Account account = Account.builder().id( 1L ).user( user ).build();
        when( accountDao.findById( any() ) )
                .thenReturn( Optional.of( account ) );
        UserSignUpDto userSignUpDto = UserSignUpDto.builder().name( "testUser2" ).build();

        Assertions.assertThrows( AccountNotFoundApiException.class, () -> sut.updatePassword( 1L, userSignUpDto ) );
    }
}
