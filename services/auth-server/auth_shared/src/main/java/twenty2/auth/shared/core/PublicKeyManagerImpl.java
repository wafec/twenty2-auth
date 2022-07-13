package twenty2.auth.shared.core;

import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twenty2.auth.shared.cryptography.PublicKeys;
import twenty2.auth.shared.exceptions.CryptographyException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.PublicKey;
import java.util.Objects;
import java.util.Optional;

@Component
public class PublicKeyManagerImpl implements PublicKeyManager {
    private final String publicKeyPemFile;
    private final String publicKeyPemFileAlgorithm;
    private final PublicKeys publicKeys;
    private String publicKeyString;
    private PublicKey publicKey;

    public static final String PUBLIC_KEY_PEM_FILE_PROPERTY = "${public-key-pem-file:#{null}}";
    public static final String PUBLIC_KEY_PEM_FILE_ALGORITHM_DEFAULT = "RSA";
    public static final String PUBLIC_KEY_PEM_FILE_ALGORITHM_PROPERTY = "${public-key-pem-file-algorithm:" +
            PUBLIC_KEY_PEM_FILE_ALGORITHM_DEFAULT + "}";

    public PublicKeyManagerImpl(
            @Value( PUBLIC_KEY_PEM_FILE_PROPERTY ) String publicKeyPemFile,
            @Value( PUBLIC_KEY_PEM_FILE_ALGORITHM_PROPERTY ) String publicKeyPemFileAlgorithm,
            PublicKeys publicKeys )
            throws CryptographyException, IOException {
        this.publicKeyPemFile = publicKeyPemFile;
        this.publicKeyPemFileAlgorithm = publicKeyPemFileAlgorithm;
        this.publicKeys = Objects.requireNonNull( publicKeys );
        initializeComponent();
    }

    private void initializeComponent() throws CryptographyException, IOException {
        publicKey = publicKeys.fromPemFile( publicKeyPemFile(), publicKeyPemFileAlgorithm );
        publicKeyString = new String( Files.readAllBytes( new File( publicKeyPemFile() ).toPath() ) );
    }

    private String publicKeyPemFile() {
        return Optional.ofNullable( publicKeyPemFile )
                .orElse( Resources.getResource( "core/public.pem" ).getFile() );
    }

    @Override
    public String contentString() {
        return publicKeyString;
    }

    @Override
    public PublicKey publicKey() {
        return publicKey;
    }
}
