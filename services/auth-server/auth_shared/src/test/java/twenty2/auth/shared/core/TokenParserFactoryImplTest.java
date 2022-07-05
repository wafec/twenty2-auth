package twenty2.auth.shared.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class TokenParserFactoryImplTest {
    @Mock
    private ObjectHashParserFactory objectHashParserFactory;

    @Mock
    private SignatureValidator signatureValidator;

    @Mock
    private BearerParser bearerParser;

    private TokenParserFactoryImpl sut;

    private static final String TOKEN = "ANY_TOKEN";

    @BeforeEach
    private void setUp() {
        sut = new TokenParserFactoryImpl( objectHashParserFactory, signatureValidator, bearerParser );
    }

    @Test
    void whenBuilding_ShouldReturnTokenParserImpl() throws Exception {
        ObjectHashParser objectHashParserJwtHeaderDto = mock( ObjectHashParser.class );
        lenient().when( objectHashParserFactory.build( eq( JwtHeaderDto.class ), any() ) )
                .thenReturn( objectHashParserJwtHeaderDto );
        when( objectHashParserJwtHeaderDto.getInstance() ).thenReturn( new JwtHeaderDto() );
        ObjectHashParser objectHashParserJwtPayloadDto = mock( ObjectHashParser.class );
        when( objectHashParserFactory.build( eq( JwtPayloadDto.class ), any() ) )
                .thenReturn( objectHashParserJwtPayloadDto );
        lenient().when( objectHashParserJwtPayloadDto.getInstance() ).thenReturn( new JwtPayloadDto() );
        when( bearerParser.parse( any() ) ).then( Answers.RETURNS_DEEP_STUBS );
        when( signatureValidator.validate( any(), any(), any() ) ).thenReturn( true );

        TokenParser tokenParser = sut.build( TOKEN );

        assertThat( tokenParser, instanceOf( TokenParserImpl.class ) );
    }
}
