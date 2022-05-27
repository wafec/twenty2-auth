package twenty2.auth.shared.api;

import twenty2.auth.shared.dto.AccountCreationDto;
import twenty2.auth.shared.dto.AccountDto;
import twenty2.auth.shared.dto.PersonalInfoDto;
import twenty2.auth.shared.dto.UserSignUpDto;
import twenty2.auth.shared.exceptions.api.AccountNotFoundApiException;

public interface AccountApi {
    String ACCOUNT_RESOURCE_PREFIX = "auth.account";
    String ACCOUNT_RESOURCE_WRITE_PERMISSION = ACCOUNT_RESOURCE_PREFIX + ".write";

    AccountDto create( AccountCreationDto accountCreation );
    AccountDto updateAccount( Long id, PersonalInfoDto account ) throws AccountNotFoundApiException;
    AccountDto updatePassword( Long id, UserSignUpDto userSignUpDto ) throws AccountNotFoundApiException;
}
