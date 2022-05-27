package twenty2.auth.api.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import twenty2.auth.api.entities.Claim;

import java.util.List;

@Repository
public interface ClaimDao extends CrudRepository<Claim, Long> {
    @Query( value = "SELECT c.* FROM claims c WHERE c.id IN ( ( SELECT uc.claim_id FROM user_claims uc WHERE uc.user_id = ?1 ) UNION ( SELECT rc.claim_id FROM role_claims rc, user_roles ur WHERE ur.user_id = ?1 AND ur.role_id = rc.role_id ) )", nativeQuery = true )
    List<Claim> findClaimByUser( Long userId );
}
