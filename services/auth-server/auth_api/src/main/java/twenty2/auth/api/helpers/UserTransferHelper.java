package twenty2.auth.api.helpers;

import twenty2.auth.shared.dto.UserDto;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

public interface UserTransferHelper {
    UserDto getUser( Long userId ) throws ResourceNotFoundApiException;
}
