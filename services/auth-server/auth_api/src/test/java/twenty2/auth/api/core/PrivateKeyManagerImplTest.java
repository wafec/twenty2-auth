package twenty2.auth.api.core;

import com.google.common.io.Resources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import twenty2.auth.api.extensions.BouncySecurityExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith( BouncySecurityExtension.class )
public class PrivateKeyManagerImplTest {
    private PrivateKeyManagerImpl sut;

    public static final String PRIVATE_PEM_PATH = "core/private.pem";

    @BeforeEach
    private void setup() throws GeneralSecurityException, IOException {
        sut = new PrivateKeyManagerImpl( Resources.getResource( PRIVATE_PEM_PATH ).getFile(), "RSA" );
    }

    @Test
    void whenGettingContentString_ShouldReturnPlainText() throws IOException {
        String result = sut.contentString();

        assertThat( result, equalTo(
                Files.readString( new File( Resources.getResource( PRIVATE_PEM_PATH ).getPath() ).toPath() ) ) );
    }

    @Test
    void whenGettingPrivateKeyInstance_ShouldReturnNonNullObject() {
        PrivateKey result = sut.privateKey();

        assertThat( result, notNullValue() );
    }
}
