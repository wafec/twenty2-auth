package twenty2.auth.api.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import twenty2.auth.api.entities.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {
    @Modifying
    @Query( value = "INSERT INTO role_claims ( role_id, claim_id ) VALUES ( ?1, ?2 )", nativeQuery = true )
    void addClaimToRole( Long roleId, Long claimId );

    @Modifying
    @Query( value = "DELETE FROM role_claims where role_id = ?1 AND claim_id = ?2", nativeQuery = true )
    void removeClaimFromRole( Long roleId, Long claimId );
}
