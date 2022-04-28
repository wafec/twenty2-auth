package auth.shared.core;

import auth.shared.exceptions.ObjectDecryptionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.crypto.Cipher;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ObjectDecryptionImpl implements ObjectDecryption {
    private RSAPrivateKey privateKey;
    private final ObjectMapper objectMapper;

    public ObjectDecryptionImpl(
            String privateKeyPemFile,
            ObjectMapper objectMapper
    )
            throws GeneralSecurityException, IOException {
        this.objectMapper = objectMapper;
        loadPrivateKey( privateKeyPemFile );
    }

    private void loadPrivateKey( final String privateKeyPemFile ) throws GeneralSecurityException, IOException {
        try ( FileReader reader = new FileReader( privateKeyPemFile ) ) {
            PemReader pemReader = new PemReader( reader );
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privateKeySpecification = new PKCS8EncodedKeySpec( content );
            privateKey = ( RSAPrivateKey ) KeyFactory.getInstance( "RSA" )
                    .generatePrivate( privateKeySpecification );
        }
    }

    @Override
    public <T> T decrypt(String encryptedMessage, Class<T> valueType )
            throws GeneralSecurityException, ObjectDecryptionException {
        Cipher decryptCipher = Cipher.getInstance( "RSA" );
        decryptCipher.init( Cipher.DECRYPT_MODE, privateKey );
        byte[] encryptedBytes = Base64.getDecoder().decode( encryptedMessage );
        byte[] decryptedBytes = decryptCipher.doFinal( encryptedBytes );
        try {
            return generateObject( new String( decryptedBytes, StandardCharsets.UTF_8 ), valueType );
        } catch ( JsonProcessingException exc ) {
            throw new ObjectDecryptionException();
        }
    }

    private <T> T generateObject( String decryptedMessage, Class<T> valueType ) throws JsonProcessingException {
        return objectMapper.readValue( decryptedMessage, valueType );
    }
}
