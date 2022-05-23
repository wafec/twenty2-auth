package twenty2.auth.shared.api;

import twenty2.auth.shared.dto.ClaimCreationDto;
import twenty2.auth.shared.dto.ClaimDto;
import twenty2.auth.shared.dto.RoleCreationDto;
import twenty2.auth.shared.dto.RoleDto;
import twenty2.auth.shared.dto.UserDto;
import twenty2.auth.shared.exceptions.ResourceNotFoundException;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

import java.util.List;

public interface AuthorizationApi {
    String AUTHORIZATION_RESOURCE_PREFIX = "auth.authorization";
    String AUTHORIZATION_RESOURCE_READ_PERMISSION = AUTHORIZATION_RESOURCE_PREFIX + ".read";
    String AUTHORIZATION_RESOURCE_WRITE_PERMISSION = AUTHORIZATION_RESOURCE_PREFIX + ".write";

    ClaimDto createClaim( ClaimCreationDto claimCreationDto );
    RoleDto createRole( RoleCreationDto roleCreationDto ) throws ResourceNotFoundException;
    RoleDto addClaimToRole( Long roleId, List<Long> claimIds  ) throws ResourceNotFoundApiException;
    RoleDto removeClaimFromRole( Long roleId, List<Long> claimIds ) throws ResourceNotFoundApiException;
    UserDto addClaimToUser( Long userId, List<Long> claimIds ) throws ResourceNotFoundApiException;
    UserDto removeClaimFromUser( Long userId, List<Long> claimIds ) throws ResourceNotFoundApiException;
    UserDto addRoleToUser( Long userId, List<Long> claimIds ) throws ResourceNotFoundApiException;
    UserDto removeRoleFromUser( Long userId, List<Long> claimIds ) throws ResourceNotFoundApiException;
}
