package twenty2.auth.api.controllers;

import twenty2.auth.api.dao.AccountDao;
import twenty2.auth.api.dao.UserDao;
import twenty2.auth.api.entities.Account;
import twenty2.auth.api.entities.User;
import twenty2.auth.api.mappers.AccountMapper;
import twenty2.auth.api.mappers.UserMapper;
import twenty2.auth.shared.dto.AccountCreationDto;
import twenty2.auth.shared.api.AccountApi;
import twenty2.auth.shared.dto.AccountDto;
import twenty2.auth.shared.dto.PersonalInfoDto;
import twenty2.auth.shared.dto.UserSignUpDto;
import twenty2.auth.shared.exceptions.api.AccountNotFoundApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping( "/account" )
public class AccountApiImpl implements AccountApi {
    private final AccountDao accountDao;
    private final UserDao userDao;

    private final AccountMapper accountMapper;
    private final UserMapper userMapper;

    @Autowired
    public AccountApiImpl( AccountDao accountDao,
                           UserDao userDao,
                           AccountMapper accountMapper,
                           UserMapper userMapper ) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.accountMapper = accountMapper;
        this.userMapper = userMapper;
    }

    @Override
    @PostMapping( "/create" )
    @Transactional
    public AccountDto create( @Valid @RequestBody AccountCreationDto accountCreationDto ) {
        Account account = accountMapper.map( accountCreationDto.getPersonalInfo() );
        User user = userMapper.map( accountCreationDto.getUser() );

        userDao.save( user );
        account.setUser( user );
        accountDao.save( account );

        return accountMapper.map( account );
    }

    @Override
    @PutMapping( "/update-account/{id}" )
    @Secured( ACCOUNT_RESOURCE_WRITE_PERMISSION )
    public AccountDto update( @PathVariable( "id" ) Long id, @Valid @RequestBody PersonalInfoDto accountDto )
            throws AccountNotFoundApiException {
        Account account = accountDao.findById( id ).orElseThrow( AccountNotFoundApiException::new );

        accountMapper.map( accountDto, account );
        accountDao.save( account );

        return accountMapper.map( account );
    }

    @Override
    @PutMapping( "/update-password/{id}" )
    @Secured( ACCOUNT_RESOURCE_WRITE_PERMISSION )
    public AccountDto updatePassword( @PathVariable( "id" ) Long id, @Valid @RequestBody UserSignUpDto userSignUpDto )
            throws AccountNotFoundApiException {
        Account account = accountDao.findById( id ).orElseThrow( AccountNotFoundApiException::new );
        User user = account.getUser();

        if ( !Objects.equals( user.getName(), userSignUpDto.getName() ) ) {
            throw new AccountNotFoundApiException();
        }

        userMapper.map( userSignUpDto, user );
        userDao.save( user );

        return accountMapper.map( account );
    }
}
