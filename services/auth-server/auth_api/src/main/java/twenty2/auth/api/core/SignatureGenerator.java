package twenty2.auth.api.core;

import java.security.GeneralSecurityException;

public interface SignatureGenerator {
    String signObject( String alg, ObjectHashGenerator hashGenerator ) throws GeneralSecurityException;
}
