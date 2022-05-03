package auth.shared.core;

import com.google.common.io.Resources;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

public class PublicKeyManagerImpl implements PublicKeyManager {
    private final String publicKeyPemFile;
    private final String publicKeyPemFileAlgorithm;
    private String publicKeyString;
    private PublicKey publicKey;

    public PublicKeyManagerImpl(String publicKeyPemFile,
                                String publicKeyPemFileAlgorithm )
            throws GeneralSecurityException, IOException {
        this.publicKeyPemFile = publicKeyPemFile;
        this.publicKeyPemFileAlgorithm = publicKeyPemFileAlgorithm;
        initializeComponent();
    }

    private void initializeComponent() throws GeneralSecurityException, IOException {
        KeyFactory keyFactory = KeyFactory.getInstance( publicKeyPemFileAlgorithm );
        try( FileReader fileReader = new FileReader( publicKeyPemFile() ) ) {
            PemReader pemReader = new PemReader( fileReader );
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec( content );
            publicKey = keyFactory.generatePublic( publicKeySpec );
        }
        publicKeyString = new String(Files.readAllBytes( new File( publicKeyPemFile() ).toPath() ) );
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
