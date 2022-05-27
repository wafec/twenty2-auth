package twenty2.auth.api.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twenty2.auth.api.dao.RoleDao;
import twenty2.auth.api.entities.Role;
import twenty2.auth.api.mappers.RoleMapper;
import twenty2.auth.shared.dto.RoleDto;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

@Component
public class RoleTransferHelperImpl implements RoleTransferHelper {
    private final RoleDao roleDao;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleTransferHelperImpl( RoleDao roleDao, RoleMapper roleMapper ) {
        this.roleDao = roleDao;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto getRole(Long roleId) throws ResourceNotFoundApiException {
        Role role = roleDao.findById( roleId )
                .orElseThrow( () -> new ResourceNotFoundApiException( Role.class.getSimpleName() ) );
        return roleMapper.map( role );
    }
}
