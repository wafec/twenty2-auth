package auth.shared.api;

import auth.shared.dto.jwt.JwtPayloadDto;
import auth.shared.exceptions.TokenParserException;

public interface SignatureApi {
    String SIGNATURE_RESOURCE_PREFIX = "auth.signature";
    String SIGNATURE_RESOURCE_READ_PERMISSION = SIGNATURE_RESOURCE_PREFIX + ".read";

    String getPublicKey();

    JwtPayloadDto validate( String authorization ) throws TokenParserException;
}
