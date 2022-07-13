package twenty2.auth.api.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import twenty2.auth.api.entities.Account;
import twenty2.auth.api.extensions.BouncySecurityExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@AutoConfigureJson
@EntityScan( "twenty2.auth.api.entities" )
@ExtendWith( BouncySecurityExtension.class )
public class AccountDaoTest {
    @Autowired
    private AccountDao accountDao;

    public static final String ACCOUNT_DAO_TEST_SQL = "classpath:dao/account-dao-test.sql";

    @Test
    @Sql( ACCOUNT_DAO_TEST_SQL )
    void whenGettingSingleAccount_ShouldBeAbleToRetrieveUserFromSameInstance() {
        Optional<Account> accountOptional = accountDao.findById( 1L );

        assertThat( accountOptional.orElseThrow().getFirstName(), equalTo( "test_user1" ) );
        assertThat( accountOptional.orElseThrow().getUser().getName(), equalTo( "test_user1" ) );
    }
}
