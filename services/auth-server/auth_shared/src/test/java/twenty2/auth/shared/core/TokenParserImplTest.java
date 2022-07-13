package twenty2.auth.shared.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;
import twenty2.auth.shared.exceptions.SignatureValidatorException;
import twenty2.auth.shared.exceptions.TokenParserException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class TokenParserImplTest {
    @Mock( answer = Answers.RETURNS_DEEP_STUBS )
    private ObjectHashParserFactory objectHashParserFactory;

    @Mock( answer = Answers.RETURNS_DEEP_STUBS )
    private SignatureValidator signatureValidator;

    @Mock( answer = Answers.RETURNS_DEEP_STUBS )
    private BearerParser bearerParser;

    private TokenParserImpl sut;

    private static final String VALID_TOKEN = "dGVzdA==";

    @Test
    void testInitialization_WhenTokenIsValid_ShouldSucceed() throws Exception {
        ObjectHashParser objectHashParser_Header = mock( ObjectHashParser.class );
        ObjectHashParser objectHashParser_Payload = mock( ObjectHashParser.class );
        lenient()
                .when( objectHashParserFactory.build( eq( JwtHeaderDto.class ), any() ) )
                .thenReturn( objectHashParser_Header );
        lenient()
                .when( objectHashParserFactory.build( eq( JwtPayloadDto.class ), any() ) )
                .thenReturn( objectHashParser_Payload );

        when( objectHashParser_Header.getInstance() ).thenReturn( new JwtHeaderDto() );
        when( objectHashParser_Payload.getInstance() ).thenReturn( new JwtPayloadDto() );
        when( signatureValidator.validate( any(), any(), any() ) ).thenReturn( true );

        TokenParserImpl sut = new TokenParserImpl( VALID_TOKEN, objectHashParserFactory,
                signatureValidator, bearerParser );

        verify( objectHashParser_Header, atLeast( 2 ) ).getInstance();
        verify( objectHashParser_Payload, atLeast( 1 ) ).getInstance();
        assertThat( sut.header(), notNullValue() );
        assertThat( sut.payload(), notNullValue() );
    }

    @Test
    void testInitialization_WhenSignatureValidationFails_ShouldThrowAnException() throws Exception {
        ObjectHashParser objectHashParser = mock( ObjectHashParser.class );
        when( objectHashParserFactory.build( any(), any() ) ).thenReturn( objectHashParser );
        when( signatureValidator.validate( any(), any(), any() ) ).thenThrow( SignatureValidatorException.class );

        Assertions.assertThrows( TokenParserException.class, () -> new TokenParserImpl( VALID_TOKEN,
                objectHashParserFactory,
                signatureValidator, bearerParser) );
    }

    @Test
    void testInitialization_WhenSignatureIsNotValid_ShouldThrowAnException() throws Exception {
        ObjectHashParser objectHashParser = mock( ObjectHashParser.class );
        when( objectHashParserFactory.build( any(), any() ) ).thenReturn( objectHashParser );
        when( signatureValidator.validate( any(), any(), any() ) ).thenReturn( false );

        Assertions.assertThrows( TokenParserException.class, () -> new TokenParserImpl( VALID_TOKEN,
                objectHashParserFactory,
                signatureValidator, bearerParser) );
    }
}
