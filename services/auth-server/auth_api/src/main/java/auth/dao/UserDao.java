package auth.dao;

import auth.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByNameAndPassword( String user, String password );
}
