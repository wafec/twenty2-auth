package auth.api.controllers;

import auth.api.dao.AccountDao;
import auth.api.dao.UserDao;
import auth.api.entities.Account;
import auth.api.entities.User;
import auth.api.mappers.AccountMapper;
import auth.api.mappers.UserMapper;
import auth.shared.dto.AccountCreationDto;
import auth.shared.api.AccountApi;
import auth.shared.dto.AccountDto;
import auth.shared.dto.AccountDetailsDto;
import auth.shared.dto.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AccountDto create( @RequestBody AccountCreationDto accountCreationDto ) {
        Account account = accountMapper.map( accountCreationDto.getDetails() );
        User user = userMapper.map( accountCreationDto.getUser() );

        userDao.save( user );
        account.setUser( user );
        accountDao.save( account );

        return accountMapper.map( account );
    }

    @Override
    @PutMapping( "/update-account/{id}" )
    @Secured( ACCOUNT_RESOURCE_WRITE_PERMISSION )
    public AccountDto update( @PathVariable( "id" ) Long id, @RequestBody AccountDetailsDto accountDto ) {
        Account account = accountDao.findById( id ).orElseThrow();

        accountMapper.map( accountDto, account );
        accountDao.save( account );

        return accountMapper.map( account );
    }

    @Override
    @PutMapping( "/update-password/{id}" )
    @Secured( ACCOUNT_RESOURCE_WRITE_PERMISSION )
    public AccountDto updatePassword( @PathVariable( "id" ) Long id, @RequestBody UserSignUpDto userSignUpDto ) {
        Account account = accountDao.findById( id ).orElseThrow();
        User user = account.getUser();

        userMapper.map( userSignUpDto, user );
        userDao.save( user );

        return accountMapper.map( account );
    }
}
