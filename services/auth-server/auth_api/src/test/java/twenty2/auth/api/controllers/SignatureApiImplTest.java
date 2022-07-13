package twenty2.auth.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.shared.core.BearerParser;
import twenty2.auth.shared.core.PublicKeyManager;
import twenty2.auth.shared.core.TokenParserFactory;
import twenty2.auth.shared.exceptions.TokenParserException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class SignatureApiImplTest {
    @Mock
    private PublicKeyManager publicKeyManager;

    @Mock( answer = Answers.RETURNS_DEEP_STUBS )
    private TokenParserFactory tokenParserFactory;

    @Mock
    private BearerParser bearerParser;

    SignatureApiImpl sut;

    @BeforeEach
    void setup() {
        sut = new SignatureApiImpl( publicKeyManager, tokenParserFactory, bearerParser );
    }

    @Test
    void whenValidating() throws TokenParserException {
        when( bearerParser.parse( any() ) )
                .then(Answers.RETURNS_DEEP_STUBS );
        String authorizationValue = "test";

        sut.validate( authorizationValue );

        verify( tokenParserFactory.build( any() ) ).payload();
    }

    @Test
    void whenProvidingThePublicKey() {
        sut.getPublicKey();

        verify( publicKeyManager ).contentString();
    }
}
