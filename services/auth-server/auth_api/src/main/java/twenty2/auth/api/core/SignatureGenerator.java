package twenty2.auth.api.core;

import twenty2.auth.api.exceptions.CryptographyException;

public interface SignatureGenerator {
    String signObject( String alg, ObjectHashGenerator hashGenerator ) throws CryptographyException;
}
