package auth.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

@Component
public class ObjectEncryptionImpl implements ObjectEncryption {
    private RSAPublicKey publicKey;
    private final ObjectMapper objectMapper;

    private static final String RSA_PUBLIC_RESOURCE_PATH = "core/rsa.public";

    @Autowired
    public ObjectEncryptionImpl(
            @Value( "${public-key-pem-file:#{null}}" ) String publicKeyPemFile,
            ObjectMapper objectMapper
    )
            throws GeneralSecurityException, IOException {
        this.objectMapper = objectMapper;
        loadPublicKey( publicKeyPemFileOrDefault( publicKeyPemFile ) );
    }

    private String publicKeyPemFileOrDefault( String publicKeyPemFile ) {
        return Optional.ofNullable( publicKeyPemFile ).orElse(
                Resources.getResource( RSA_PUBLIC_RESOURCE_PATH ).getFile()
        );
    }

    private void loadPublicKey( final String publicKeyPemFile ) throws GeneralSecurityException, IOException {
        try ( FileReader keyReader = new FileReader( publicKeyPemFile ) ) {
            PemReader pemReader = new PemReader( keyReader );
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec publicKeySpecification = new X509EncodedKeySpec( content );
            publicKey = ( RSAPublicKey ) KeyFactory.getInstance( "RSA" )
                    .generatePublic( publicKeySpecification );
        }
    }

    @Override
    public String encrypt( Object obj ) throws GeneralSecurityException {
        Cipher encryptCipher = Cipher.getInstance( "RSA" );
        encryptCipher.init( Cipher.ENCRYPT_MODE, publicKey );
        byte[] encryptedBytes = encryptCipher.doFinal( generateObjectMessageBytes( obj ) );
        return Base64.getEncoder().encodeToString( encryptedBytes );
    }

    @SneakyThrows
    private byte[] generateObjectMessageBytes( Object obj ) {
        return objectMapper.writeValueAsString( obj )
                .getBytes( StandardCharsets.UTF_8 );
    }
}
