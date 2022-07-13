package twenty2.auth.api.core;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.api.cryptography.Encrypter;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class SignatureGeneratorImplTest {
    @Mock
    private PrivateKeyManager privateKeyManager;

    @Mock
    private Encrypter encrypter;

    private SignatureGeneratorImpl sut;

    @BeforeEach
    private void setUp() {
        sut = new SignatureGeneratorImpl( privateKeyManager, encrypter );
    }

    @Test
    void testSignObject_WhenAllFine_ShouldCallDependentMethods() throws Exception {
        String test = "test";
        String expectedResult = "dGVzdA";
        byte[] data = test.getBytes( StandardCharsets.UTF_8 );
        when( encrypter.encrypt( any(), any(), any() ) ).thenReturn( data );
        ObjectHashGenerator objectHashGenerator = mock( ObjectHashGenerator.class );

        String result = sut.signObject( "", objectHashGenerator );

        assertThat( result, is( notNullValue() ) );
        assertThat( result, equalTo( expectedResult ) );
    }
}
