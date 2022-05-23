package twenty2.auth.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import twenty2.auth.api.entities.Claim;

@Repository
public interface ClaimDao extends CrudRepository<Claim, Long> {
}
