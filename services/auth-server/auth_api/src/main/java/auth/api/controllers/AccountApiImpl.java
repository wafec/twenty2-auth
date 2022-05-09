package auth.api.controllers;

import auth.api.dao.AccountDao;
import auth.shared.dto.AccountCreationDto;
import auth.shared.api.AccountApi;
import auth.shared.dto.AccountDto;
import auth.shared.dto.AccountDetailsDto;
import auth.shared.dto.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

    @Autowired
    public AccountApiImpl( AccountDao accountDao ) {
        this.accountDao = accountDao;
    }

    @Override
    @PostMapping( "/create" )
    public AccountDto create( @RequestBody AccountCreationDto accountCreation ) {
        return null;
    }

    @Override
    @PutMapping( "/update-account/{id}" )
    @Secured( ACCOUNT_RESOURCE_WRITE_PERMISSION )
    public AccountDto update( @PathVariable( "id" ) Long id, @RequestBody AccountDetailsDto account ) {
        return null;
    }

    @Override
    @PutMapping( "/update-password/{id}" )
    @Secured( ACCOUNT_RESOURCE_WRITE_PERMISSION )
    public AccountDto updatePassword( @PathVariable( "id" ) Long id, @RequestBody UserSignUpDto userSignUpDto ) {
        return null;
    }
}
