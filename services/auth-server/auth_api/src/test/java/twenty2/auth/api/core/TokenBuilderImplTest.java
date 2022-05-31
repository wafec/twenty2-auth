package twenty2.auth.api.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.api.entities.Claim;
import twenty2.auth.api.entities.User;
import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class TokenBuilderImplTest {
    @Mock
    private User user;

    @Mock
    private SignatureGenerator signatureGenerator;

    @Mock
    private ObjectHashGeneratorFactory objectHashGeneratorFactory;

    private TokenBuilderImpl sut;

    private static final String SIGNATURE_ALGORITHM = "RSA";

    @BeforeEach
    private void setup() {
        sut = new TokenBuilderImpl( signatureGenerator, objectHashGeneratorFactory, SIGNATURE_ALGORITHM );
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
        when( user.getName() ).thenReturn( "test_user1" );
        when( objectHashGeneratorFactory.build( any() ) )
                .then( Answers.RETURNS_DEEP_STUBS );

        // Act
        String tokenString = sut.generate( user, claims );

        // Assert
        verify( user, times( 1 ) ).getName();
        verify( signatureGenerator ).signObject( any(), any() );
        verify( objectHashGeneratorFactory ).build( any( JwtHeaderDto.class ) );
        verify( objectHashGeneratorFactory ).build( any( JwtPayloadDto.class ) );
    }
}
