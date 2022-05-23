package twenty2.auth.api.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import twenty2.auth.api.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByNameAndPassword( String user, String password );

    @Modifying
    @Query( value = "INSERT INTO user_claims (user_id, claim_id) VALUES (?1, ?2)", nativeQuery = true )
    Integer addClaimToUser( Long userId, Long claimId );

    @Modifying
    @Query( value = "DELETE FROM user_claims WHERE user_id = ?1 AND claim_id = ?2", nativeQuery = true )
    Integer removeClaimFromUser( Long userId, Long claimId );

    @Modifying
    @Query( value = "INSERT INTO user_roles (user_id, role_id) VALUES (?1, ?2)", nativeQuery = true )
    Integer addRoleToUser( Long userId, Long roleId );

    @Modifying
    @Query( value = "DELETE FROM user_roles WHERE user_id = ?1 AND role_id = ?2", nativeQuery = true )
    Integer removeRoleFromUser( Long userId, Long roleId );
}
