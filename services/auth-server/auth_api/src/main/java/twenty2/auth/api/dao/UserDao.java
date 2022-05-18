package twenty2.auth.api.dao;

import twenty2.auth.api.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByNameAndPassword( String user, String password );
}
