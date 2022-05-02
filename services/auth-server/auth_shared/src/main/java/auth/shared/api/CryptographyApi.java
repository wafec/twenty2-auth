package auth.shared.api;

import auth.shared.exceptions.CryptographyKeyNotFoundException;

public interface CryptographyApi {
    String getPrivateKey() throws CryptographyKeyNotFoundException;
}
