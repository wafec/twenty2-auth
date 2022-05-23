package twenty2.auth.api.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twenty2.auth.api.dao.UserDao;
import twenty2.auth.api.entities.User;
import twenty2.auth.api.mappers.UserMapper;
import twenty2.auth.shared.dto.UserDto;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

@Component
public class UserTransferHelperImpl implements UserTransferHelper {
    private final UserDao userDao;
    private final UserMapper userMapper;

    @Autowired
    public UserTransferHelperImpl( UserDao userDao, UserMapper userMapper ) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getUser( Long userId ) throws ResourceNotFoundApiException {
        User user = userDao.findById( userId )
                .orElseThrow( () -> new ResourceNotFoundApiException( User.class.getSimpleName() ) );
        return userMapper.map( user );
    }
}
