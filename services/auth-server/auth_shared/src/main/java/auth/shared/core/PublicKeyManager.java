package auth.shared.core;

import java.security.PublicKey;

public interface PublicKeyManager {
    String contentString();

    PublicKey publicKey();
}
