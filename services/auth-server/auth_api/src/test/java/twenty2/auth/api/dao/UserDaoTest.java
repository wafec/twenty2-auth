package twenty2.auth.api.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import twenty2.auth.api.entities.User;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@DataJpaTest
@EntityScan( "twenty2.auth.api.entities" )
@AutoConfigureJson
@ExtendWith( DaoTestExtension.class )
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    public static final String USER_DAO_TEST_SQL = "classpath:dao/user-dao-test.sql";

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

    @Test
    @Sql( USER_DAO_TEST_SQL )
    void findById_UserExists_ReturnWithRoles() {
        Optional<User> actualUser = userDao.findById( 1L );

        assertThat(
                actualUser.orElseThrow().getRoles(),
                hasItems(
                        hasProperty( "name", equalTo( "test_admin" ) )
                )
        );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    void addClaimToUser_WhenClaimExists_ShouldContainClaim() {
        Integer changed = userDao.addClaimToUser( 1L, 3L );

        assertThat( changed, equalTo( 1 ) );
        assertThat( userDao.findById( 1L ).orElseThrow().getClaims(), hasItem(
                hasProperty( "value", equalTo( "test_claim_user1.claim3" ) )
        ) );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    void removeClaimFromUser_WhenClaimExists_ShouldNotContainClaim() {
        Integer changed = userDao.removeClaimFromUser( 1L, 2L );

        assertThat( changed, equalTo( 1 ) );
        assertThat( userDao.findById( 1L ).orElseThrow().getClaims(), not( hasItem(
                hasProperty( "value", equalTo( "test_claim_user1.claim2" ) )
        ) ) );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    void addRoleToUser_WhenRoleExists_ShouldContainRole() {
        Integer changed = userDao.addRoleToUser( 1L, 2L );

        assertThat( changed, equalTo( 1 ) );
        assertThat( userDao.findById( 1L ).orElseThrow().getRoles(), hasItem(
            hasProperty( "name", equalTo( "test_user" ) )
        ) );
    }

    @Test
    @Sql( USER_DAO_TEST_SQL )
    void removeRoleFromUser_WhenRoleExists_ShouldNotContainRole() {
        Integer changed = userDao.removeRoleFromUser( 1L, 1L );

        assertThat( changed, equalTo( 1 ) );
        assertThat( userDao.findById( 1L ).orElseThrow().getRoles(), not( hasItem(
                hasProperty( "name", equalTo( "test_admin" ) )
        ) ) );
    }
}
