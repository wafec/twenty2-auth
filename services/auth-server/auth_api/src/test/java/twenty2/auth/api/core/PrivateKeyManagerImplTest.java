package twenty2.auth.api.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.api.cryptography.PrivateKeys;
import twenty2.auth.api.exceptions.CryptographyException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class PrivateKeyManagerImplTest {
    @Mock
    private PrivateKeys privateKeys;

    @Test
    void whenPrivateKeyIsValid_ShouldHaveAContent() throws Exception {
        PrivateKey privateKey = generateRandomPrivateKey();
        when( privateKeys.fromPemFile( any(), any() ) ).thenReturn( privateKey );

        PrivateKeyManagerImpl sut = new PrivateKeyManagerImpl( null, "RSA", privateKeys );

        assertThat( sut.privateKey(), is( notNullValue() ) );
        assertThat( sut.privateKey(), equalTo( privateKey ) );
        assertThat( sut.contentString(), is( notNullValue() ) );
    }

    private PrivateKey generateRandomPrivateKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        keyPairGenerator.initialize( 2048 );
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair.getPrivate();
    }

    @Test
    void whenPrivateKeyIsInvalid_ShouldThrowAnException() throws Exception {
        doThrow( CryptographyException.class ).when( privateKeys ).fromPemFile( any(), any() );
        Assertions.assertThrows( CryptographyException.class, () ->
                new PrivateKeyManagerImpl( null, "RSA", privateKeys ) );
    }
}
