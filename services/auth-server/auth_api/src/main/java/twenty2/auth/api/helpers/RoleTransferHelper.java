package twenty2.auth.api.helpers;

import twenty2.auth.shared.dto.RoleDto;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

public interface RoleTransferHelper {
    RoleDto getRole( Long roleId ) throws ResourceNotFoundApiException;
}
