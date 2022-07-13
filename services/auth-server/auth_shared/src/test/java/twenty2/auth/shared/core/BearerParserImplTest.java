package twenty2.auth.shared.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import twenty2.auth.shared.dto.jwt.BearerDto;
import twenty2.auth.shared.exceptions.TokenParserException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BearerParserImplTest {
    private BearerParserImpl sut;

    private static final String HEADER = "header";
    private static final String PAYLOAD = "payload";
    private static final String SIGNATURE = "signature";

    @BeforeEach
    private void setup() {
        sut = new BearerParserImpl();
    }

    private static List<String> provideBearerTokens() {
        return List.of( String.format( "Bearer %s.%s.%s", HEADER, PAYLOAD, SIGNATURE ),
                String.format( "%s.%s.%s", HEADER, PAYLOAD, SIGNATURE ) );
    }

    @ParameterizedTest
    @MethodSource( "provideBearerTokens" )
    void testParse_WhenTokenIsValid_ShouldReturnValidObject( String bearerToken ) throws Exception {
        BearerDto bearerDto = sut.parse( bearerToken );

        assertThat( bearerDto.getHeaderString(), equalTo( HEADER ) );
        assertThat( bearerDto.getPayloadString(), equalTo( PAYLOAD ) );
        assertThat( bearerDto.getDigitalSignatureString(), equalTo( SIGNATURE ) );
    }

    @Test
    void testParse_WhenTokenIsInvalid_ShouldThrowAnException() {
        String invalidBearerToken = "abcdefg123";

        assertThrows( TokenParserException.class, () -> sut.parse( invalidBearerToken ) );
    }
}
