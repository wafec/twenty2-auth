package auth.shared.api;

import auth.shared.dto.AccountCreationDto;
import auth.shared.dto.AccountDto;
import auth.shared.dto.AccountDetailsDto;
import auth.shared.dto.UserSignUpDto;

public interface AccountApi {
    String ACCOUNT_RESOURCE_PREFIX = "auth.account";
    String ACCOUNT_RESOURCE_WRITE_PERMISSION = ACCOUNT_RESOURCE_PREFIX + ".write";

    AccountDto create( AccountCreationDto accountCreation );
    AccountDto update( Long id, AccountDetailsDto account );
    AccountDto updatePassword( Long id, UserSignUpDto userSignUpDto );
}
