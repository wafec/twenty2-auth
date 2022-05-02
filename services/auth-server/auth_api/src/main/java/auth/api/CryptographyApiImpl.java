package auth.api;

import auth.exceptions.InvalidPrivateKeyException;
import auth.helpers.PrivateKeyHelper;
import auth.shared.api.CryptographyApi;
import auth.shared.exceptions.CryptographyKeyNotFoundException;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping( "cryptography" )
public class CryptographyApiImpl implements CryptographyApi {
    private String privateKey;
    private PrivateKeyHelper privateKeyHelper;

    @Autowired
    public CryptographyApiImpl (
            @Value( "${private-key-pem-file:#null}" ) String privateKeyPemFile,
            PrivateKeyHelper privateKeyHelper
    ) throws InvalidPrivateKeyException {
        loadPrivateKeyString( privateKeyPemFile );
    }

    private void loadPrivateKeyString( final String privateKeyPemFile ) throws InvalidPrivateKeyException {
        privateKey = privateKeyHelper.readPrivateKeyFromPemFile(
                privateKeyPemFileOrDefault( privateKeyPemFile )
        );
    }

    private String privateKeyPemFileOrDefault( String privateKeyPemFile ) {
        return Optional.ofNullable( privateKeyPemFile ).orElse(
                Resources.getResource( "core/rsa.public" ).getFile()
        );
    }

    @Override
    @GetMapping( "private-key" )
    public String getPrivateKey() throws CryptographyKeyNotFoundException {
        if (privateKey == null)
            throw new CryptographyKeyNotFoundException();
        return privateKey;
    }
}
