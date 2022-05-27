package twenty2.auth.api.dao;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import twenty2.auth.api.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.security.Security;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;

@DataJpaTest
@EntityScan( "twenty2.auth.api.entities" )
@AutoConfigureJson
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    public static final String USER_DAO_TEST_SQL = "classpath:dao/user-dao-test.sql";

    @BeforeAll
    static void init() {
        Security.addProvider( new BouncyCastleProvider() );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    void findByNameAndPassword_UserExists_ReturnUser() {
        // Setup
        // Act
        User actualUser = userDao.findByNameAndPassword( "test_user1", "Password123" );

        // Assert
        assertThat( actualUser.getName(), equalTo( "test_user1" ) );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    void findByNameAndPassword_UserNotFound_ReturnNull() {
        // Setup
        // Act
        User actualUser = userDao.findByNameAndPassword( "unknown_user", "Unknown123" );

        // Assert
        assertThat( actualUser, nullValue() );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    void findById_UserExists_ReturnWithClaims() {
        // Setup
        // Act
        Optional<User> actualUser = userDao.findById( 1L );

        // Assert
        assertThat(
                actualUser.orElseThrow().getClaims(),
                hasItems(
                        hasProperty( "value", equalTo( "test_claim_user1.claim1" ) ),
                        hasProperty( "value", equalTo( "test_claim_user1.claim2" ) )
                )
        );
    }
}
