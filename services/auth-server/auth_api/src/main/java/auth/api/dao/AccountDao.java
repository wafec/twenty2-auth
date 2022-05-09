package auth.api.dao;

import auth.api.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<Long, Account> {
}
