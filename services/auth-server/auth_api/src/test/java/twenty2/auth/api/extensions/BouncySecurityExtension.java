package twenty2.auth.api.extensions;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.security.Security;

public class BouncySecurityExtension implements BeforeAllCallback {
    @Override
    public void beforeAll( ExtensionContext context ) throws Exception {
        Security.addProvider( new BouncyCastleProvider() );
    }
}
