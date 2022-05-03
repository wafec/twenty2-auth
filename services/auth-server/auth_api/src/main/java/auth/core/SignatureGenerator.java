package auth.core;

import java.security.GeneralSecurityException;

public interface SignatureGenerator {
    String signObject( String alg, ObjectHashGenerator hashGenerator ) throws GeneralSecurityException;
}
