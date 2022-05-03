package auth.core;

import auth.entities.Claim;
import auth.entities.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class TokenBuilderImplTest {
    @Mock
    private User user;

    private TokenBuilderImpl getSut() {
        return null;
    }

    private static Stream<List<Claim>> provideClaims() {
        return Stream.of(
                new ArrayList<>(),
                null
        );
    }

    @ParameterizedTest
    @MethodSource( "provideClaims" )
    public void generate_NonNullObject_ReturnTokenString( List<Claim> claims  ) throws Exception {
        // Setup
        TokenBuilderImpl sut = getSut();
        when( user.getName() ).thenReturn( "test_user1" );
        when( user.getClaims() ).thenReturn( claims );

        // Act
        String tokenString = sut.generate( user );

        // Assert
        verify( user, times( 1 ) ).getName();
        verify( user, times( 1 ) ).getClaims();
    }
}
