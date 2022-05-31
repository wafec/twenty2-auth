package twenty2.auth.api.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import twenty2.auth.api.entities.Role;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;

@DataJpaTest
@AutoConfigureJson
@EntityScan( "twenty2.auth.api.entities" )
@ExtendWith( DaoTestExtension.class )
public class RoleDaoTest {
    @Autowired
    private RoleDao roleDao;

    public static final String ROLE_DAO_TEST_SQL = "classpath:dao/role-dao-test.sql";

    @Test
    @Sql( ROLE_DAO_TEST_SQL )
    void whenGettingARole_ShouldLoadClaims() {
        Role role = roleDao.findById( 1L ).orElseThrow();

        assertThat( role.getName(), equalTo( "test_admin" ) );
        assertThat( role.getClaims(), hasItems(
                hasProperty( "value", equalTo( "claim1" ) ),
                hasProperty( "value", equalTo( "claim2" ) )
        ));
    }

    @Test
    @Sql( ROLE_DAO_TEST_SQL )
    void whenAddingClaimToRole_ShouldContainClaim() {
        Integer changed = roleDao.addClaimToRole( 1L, 3L );

        assertThat( changed, equalTo( 1 ) );
        assertThat( roleDao.findById( 1L ).orElseThrow().getClaims(), hasItem(
                hasProperty( "value", equalTo( "claim3" ) )
        ) );
    }

    @Test
    @Sql( ROLE_DAO_TEST_SQL )
    void whenRemovingClaimFromRole_ShouldNotContainClaim() {
        Integer changed = roleDao.removeClaimFromRole( 1L, 1L );

        assertThat( changed, equalTo( 1 ) );
        assertThat( roleDao.findById( 1L ).orElseThrow().getClaims(), not( hasItem(
              hasProperty( "value", equalTo( "claim1" ) )
        ) ) );
    }
}
