package twenty2.auth.shared.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.shared.cryptography.PublicKeys;
import twenty2.auth.shared.exceptions.CryptographyException;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class PublicKeyManagerImplTest {
    @Mock
    private PublicKeys publicKeys;

    private static final String ALGORITHM = "RSA";

    @Test
    void whenPublicKeyIsValid_ShouldHaveAContent() throws Exception {
        PublicKey publicKey = generateRandomPublicKey();
        when( publicKeys.fromPemFile( any(), any() ) ).thenReturn( publicKey );
        PublicKeyManagerImpl sut = new PublicKeyManagerImpl( null, ALGORITHM, publicKeys );

        assertThat( sut.publicKey(), is( notNullValue() ) );
        assertThat( sut.publicKey(), equalTo( publicKey ) );
        assertThat( sut.contentString(), is( notNullValue() ) );
    }

    private PublicKey generateRandomPublicKey() throws GeneralSecurityException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance( ALGORITHM );
        keyGen.initialize( 2048 );
        KeyPair pair = keyGen.generateKeyPair();
        return pair.getPublic();
    }

    @Test
    void whenPublicKeyIsInvalid_ShouldThrowAnException() throws Exception {
        doThrow( CryptographyException.class ).when( publicKeys ).fromPemFile( any(), any() );
        Assertions.assertThrows( CryptographyException.class,
                () -> new PublicKeyManagerImpl( null, ALGORITHM, publicKeys ) );
    }
}
