package auth.core;

import auth.shared.core.ObjectDecryptionImpl;
import auth.shared.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class ObjectEncryptionImplTest {
    private static final String RSA_PRIVATE_RESOURCE_PATH = "core/rsa.private";
    private static final String RSA_PUBLIC_RESOURCE_PATH = "core/rsa.public";
    private static final String publicKeyPemFile = Resources.getResource( RSA_PUBLIC_RESOURCE_PATH ).getFile();
    private static final String privateKeyPemFile = Resources.getResource( RSA_PRIVATE_RESOURCE_PATH ).getFile();

    private ObjectEncryptionImpl getEncryptionSut() throws Exception {
        return new ObjectEncryptionImpl( publicKeyPemFile, new ObjectMapper());
    }

    private ObjectDecryptionImpl getDecryptionSut() throws Exception {
        return new ObjectDecryptionImpl( privateKeyPemFile, new ObjectMapper() );
    }

    @BeforeEach
    public void setUp() {
        Security.addProvider( new BouncyCastleProvider() );
    }

    @Test
    public void testEncrypt_SimpleInput() throws Exception {
        // Setup
        ObjectEncryptionImpl encryptionSut = getEncryptionSut();
        ObjectDecryptionImpl decryptionSut = getDecryptionSut();

        // Act
        String encryptedMessage = encryptionSut.encrypt( new TokenDto( "test", ImmutableList.of(
                "claimTest1", "claimTest2"
        )) );
        TokenDto decryptedObject = decryptionSut.decrypt( encryptedMessage, TokenDto.class );

        // Assert
      assertThat( decryptedObject.getUser(), equalTo( "test" ) );
      assertThat( decryptedObject.getClaims(), hasItems( "claimTest1", "claimTest2" ) );
    }
}
