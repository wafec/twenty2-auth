package twenty2.auth.api.dao;

import twenty2.auth.api.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith( SpringExtension.class )
@DataJpaTest
@EntityScan( "auth.dao" )
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    public static final String USER_DAO_TEST_SQL = "classpath:dao/user-dao-test.sql";

    @Test
    @Sql(USER_DAO_TEST_SQL)
    public void findByNameAndPassword_UserExists_ReturnUser() {
        // Setup
        // Act
        User actualUser = userDao.findByNameAndPassword( "test_user1", "Password123" );

        // Assert
        assertThat( actualUser.getName(), equalTo( "test_user1" ) );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    public void findByNameAndPassword_UserNotFound_ReturnNull() {
        // Setup
        // Act
        User actualUser = userDao.findByNameAndPassword( "unknown_user", "Unknown123" );

        // Assert
        assertThat( actualUser, nullValue() );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    public void findById_UserExists_ReturnWithClaims() {
        // Setup
        // Act
        Optional<User> actualUser = userDao.findById( 1L );

        // Assert
        assertThat(
                actualUser.orElseThrow().getClaims(),
                hasItems(
                        hasProperty( "description", equalTo( "test_claim_user1.claim1" ) ),
                        hasProperty( "description", equalTo( "test_claim_user1.claim2" ) )
                )
        );
    }
}
