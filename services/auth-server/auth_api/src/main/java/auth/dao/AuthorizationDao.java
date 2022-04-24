package auth.dao;

import auth.entities.Authorization;
import org.springframework.data.repository.CrudRepository;

public interface AuthorizationDao extends CrudRepository<Authorization, Long> {
}
