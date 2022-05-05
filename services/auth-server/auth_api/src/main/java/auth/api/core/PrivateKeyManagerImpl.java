package auth.api.core;

import com.google.common.io.Resources;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Optional;

@Component
public class PrivateKeyManagerImpl implements PrivateKeyManager {
    private final String privateKeyPemFile;
    private final String privateKeyPemFileAlgorithm;
    private String privateKeyString;
    private PrivateKey privateKey;

    public static final String PRIVATE_KEY_PEM_FILE_ALGORITHM_DEFAULT = "RSA";
    public static final String PRIVATE_KEY_PEM_FILE_PROPERTY = "${private-key-pem-file:#{null}}";
    public static final String PRIVATE_KEY_PEM_FILE_ALGORITHM_PROPERTY = "${private-key-pem-file-algorithm:" +
            PRIVATE_KEY_PEM_FILE_ALGORITHM_DEFAULT + "}";

    public PrivateKeyManagerImpl( @Value( PRIVATE_KEY_PEM_FILE_PROPERTY ) String privateKeyPemFile,
                                  @Value( PRIVATE_KEY_PEM_FILE_ALGORITHM_PROPERTY ) String privateKeyPemFileAlgorithm )
            throws IOException, GeneralSecurityException {
        this.privateKeyPemFile = privateKeyPemFile;
        this.privateKeyPemFileAlgorithm = privateKeyPemFileAlgorithm;
        initializeComponent();
    }

    private void initializeComponent() throws IOException, GeneralSecurityException {
        KeyFactory keyFactory = KeyFactory.getInstance( privateKeyPemFileAlgorithm );
        try(FileReader fileReader = new FileReader( privateKeyPemFile() ) ) {
            PemReader pemReader = new PemReader( fileReader );
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec( content );
            privateKey = keyFactory.generatePrivate( privateKeySpec );
        }
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
