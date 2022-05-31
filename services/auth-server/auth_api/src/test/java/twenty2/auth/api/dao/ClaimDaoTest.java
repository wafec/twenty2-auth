package twenty2.auth.api.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import twenty2.auth.api.entities.Claim;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;

@DataJpaTest
@AutoConfigureJson
@EntityScan( "twenty2.auth.api.entities" )
@ExtendWith( DaoTestExtension.class )
public class ClaimDaoTest {
    @Autowired
    private ClaimDao claimDao;

    public static final String CLAIM_DAO_TEST_SQL = "classpath:dao/claim-dao-test.sql";

    @Test
    @Sql( CLAIM_DAO_TEST_SQL )
    void whenGettingAllClaims_ShouldMakeRetrieveAllDistinct() {
        List<Claim> claims = claimDao.findClaimByUser( 1L );

        assertThat( claims.size(), equalTo( 2 ) );
        assertThat( claims, hasItems(
                hasProperty( "value", equalTo( "claim1" ) ),
                hasProperty( "value", equalTo( "claim2" ) )
        ) );
    }
}
