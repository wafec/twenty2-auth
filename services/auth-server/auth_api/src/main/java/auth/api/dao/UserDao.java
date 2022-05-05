package auth.api.dao;

import auth.api.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByNameAndPassword( String user, String password );
}
