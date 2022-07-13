package twenty2.auth.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twenty2.auth.api.dao.ClaimDao;
import twenty2.auth.api.dao.RoleDao;
import twenty2.auth.api.dao.UserDao;
import twenty2.auth.api.entities.Claim;
import twenty2.auth.api.entities.Role;
import twenty2.auth.api.helpers.RoleTransferHelper;
import twenty2.auth.api.helpers.UserTransferHelper;
import twenty2.auth.api.mappers.ClaimMapper;
import twenty2.auth.api.mappers.RoleMapper;
import twenty2.auth.shared.api.AuthorizationApi;
import twenty2.auth.shared.dto.ClaimCreationDto;
import twenty2.auth.shared.dto.ClaimDto;
import twenty2.auth.shared.dto.RoleCreationDto;
import twenty2.auth.shared.dto.RoleDto;
import twenty2.auth.shared.dto.UserDto;
import twenty2.auth.shared.exceptions.ResourceNotFoundException;
import twenty2.auth.shared.exceptions.api.ResourceNotFoundApiException;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( "/authorization" )
public class AuthorizationApiImpl implements AuthorizationApi {
    private final ClaimDao claimDao;
    private final ClaimMapper claimMapper;
    private final RoleDao roleDao;
    private final RoleMapper roleMapper;
    private final EntityManager entityManager;
    private final UserDao userDao;
    private final UserTransferHelper userTransferHelper;
    private final RoleTransferHelper roleTransferHelper;

    @Autowired
    public AuthorizationApiImpl( ClaimDao claimDao, ClaimMapper claimMapper,
                                 RoleDao roleDao, RoleMapper roleMapper,
                                 EntityManager entityManager, UserDao userDao,
                                 UserTransferHelper userTransferHelper,
                                 RoleTransferHelper roleTransferHelper ) {
        this.claimDao = claimDao;
        this.claimMapper = claimMapper;
        this.roleDao = roleDao;
        this.roleMapper = roleMapper;
        this.entityManager = entityManager;
        this.userDao = userDao;
        this.userTransferHelper = userTransferHelper;
        this.roleTransferHelper = roleTransferHelper;
    }

    @Override
    @PostMapping( "/create-claim" )
    @Secured( AUTHORIZATION_RESOURCE_WRITE_PERMISSION )
    public ClaimDto createClaim( @Valid @RequestBody ClaimCreationDto claimCreationDto ) {
        Claim claim = claimMapper.map( claimCreationDto );
        claimDao.save( claim );
        return claimMapper.map( claim );
    }

    @Override
    @Transactional
    @PostMapping( "/create-role" )
    @Secured( AUTHORIZATION_RESOURCE_WRITE_PERMISSION )
    public RoleDto createRole( @Valid @RequestBody RoleCreationDto roleCreationDto ) throws ResourceNotFoundException {
        final Role role = roleMapper.map( roleCreationDto );
        roleDao.save( role );
        roleCreationDto.getClaimIds().forEach( claimId ->
                roleDao.addClaimToRole( role.getId(), claimId ) );
        entityManager.refresh( role );
        return roleMapper.map( role );
    }

    @Override
    @Transactional
    @PutMapping( "/add-claim-to-role/{id}" )
    @Secured( AUTHORIZATION_RESOURCE_WRITE_PERMISSION )
    public RoleDto addClaimToRole( @PathVariable( "id" ) Long roleId, @RequestBody List<Long> claimIds )
            throws ResourceNotFoundApiException {
        claimIds.forEach( claimId -> roleDao.addClaimToRole( roleId, claimId ) );
        return roleTransferHelper.getRole( roleId );
    }

    @Override
    @Transactional
    @DeleteMapping( "/remove-claim-from-role/{id}" )
    @Secured( AUTHORIZATION_RESOURCE_WRITE_PERMISSION )
    public RoleDto removeClaimFromRole( @PathVariable( "id" ) Long roleId, @RequestBody List<Long> claimIds )
            throws ResourceNotFoundApiException  {
        claimIds.forEach( claimId -> roleDao.removeClaimFromRole( roleId, claimId ) );
        return roleTransferHelper.getRole( roleId );
    }

    @Override
    @Transactional
    @PostMapping( "/add-claim-to-user/{id}" )
    @Secured( AUTHORIZATION_RESOURCE_WRITE_PERMISSION )
    public UserDto addClaimToUser( @PathVariable( "id" ) Long userId,  @RequestBody List<Long> claimIds )
            throws ResourceNotFoundApiException {
        claimIds.forEach( claimId -> userDao.addClaimToUser( userId, claimId ) );
        return userTransferHelper.getUser( userId );
    }

    @Override
    @Transactional
    @DeleteMapping( "/remove-claim-from-user/{id}" )
    @Secured( AUTHORIZATION_RESOURCE_WRITE_PERMISSION )
    public UserDto removeClaimFromUser( @PathVariable( "id" ) Long userId, @RequestBody List<Long> claimIds )
            throws ResourceNotFoundApiException {
        claimIds.forEach( claimId -> userDao.removeClaimFromUser( userId, claimId ) );
        return userTransferHelper.getUser( userId );
    }

    @Override
    @Transactional
    @PutMapping( "/add-role-to-user/{id}" )
    @Secured( AUTHORIZATION_RESOURCE_WRITE_PERMISSION )
    public UserDto addRoleToUser( @PathVariable( "id" ) Long userId, @RequestBody List<Long> roleIds )
            throws ResourceNotFoundApiException {
        roleIds.forEach( roleId -> userDao.addRoleToUser( userId, roleId ) );
        return userTransferHelper.getUser( userId );
    }

    @Override
    @Transactional
    @DeleteMapping( "/remove-role-from-user/{id}" )
    @Secured( AUTHORIZATION_RESOURCE_WRITE_PERMISSION )
    public UserDto removeRoleFromUser( @PathVariable( "id" ) Long userId, @RequestBody List<Long> roleIds)
            throws ResourceNotFoundApiException {
        roleIds.forEach(claimId -> userDao.removeRoleFromUser( userId, claimId ) );
        return userTransferHelper.getUser( userId );
    }
}
