package twenty2.auth.api.core;

import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twenty2.auth.api.cryptography.PrivateKeys;
import twenty2.auth.api.exceptions.CryptographyException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.util.Optional;

@Component
public class PrivateKeyManagerImpl implements PrivateKeyManager {
    private final String privateKeyPemFile;
    private final String privateKeyPemFileAlgorithm;
    private final PrivateKeys privateKeys;
    private String privateKeyString;
    private PrivateKey privateKey;

    public static final String PRIVATE_KEY_PEM_FILE_ALGORITHM_DEFAULT = "RSA";
    public static final String PRIVATE_KEY_PEM_FILE_PROPERTY = "${private-key-pem-file:#{null}}";
    public static final String PRIVATE_KEY_PEM_FILE_ALGORITHM_PROPERTY = "${private-key-pem-file-algorithm:" +
            PRIVATE_KEY_PEM_FILE_ALGORITHM_DEFAULT + "}";

    @Autowired
    public PrivateKeyManagerImpl(
            @Value( PRIVATE_KEY_PEM_FILE_PROPERTY ) String privateKeyPemFile,
            @Value( PRIVATE_KEY_PEM_FILE_ALGORITHM_PROPERTY ) String privateKeyPemFileAlgorithm,
            PrivateKeys privateKeys )
            throws IOException, CryptographyException {
        this.privateKeyPemFile = privateKeyPemFile;
        this.privateKeyPemFileAlgorithm = privateKeyPemFileAlgorithm;
        this.privateKeys = privateKeys;
        initializeComponent();
    }

    private void initializeComponent() throws IOException, CryptographyException {
        privateKey = privateKeys.fromPemFile( privateKeyPemFile(), privateKeyPemFileAlgorithm );
        privateKeyString = new String( Files.readAllBytes( new File( privateKeyPemFile() ).toPath() ) );
    }

    private String privateKeyPemFile() {
        return Optional.ofNullable( privateKeyPemFile )
                .orElse( Resources.getResource( "core/private.pem" ).getFile() );
    }

    @Override
    public String contentString() {
        return privateKeyString;
    }

    @Override
    public PrivateKey privateKey() {
        return privateKey;
    }
}
