package twenty2.auth.api.core;

import java.security.PrivateKey;

public interface PrivateKeyManager {
    String contentString();

    PrivateKey privateKey();
}
