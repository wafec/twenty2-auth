package twenty2.auth.shared.api;

import twenty2.auth.shared.dto.jwt.JwtPayloadDto;
import twenty2.auth.shared.exceptions.TokenParserException;

public interface SignatureApi {
    String SIGNATURE_RESOURCE_PREFIX = "auth.signature";
    String SIGNATURE_RESOURCE_READ_PERMISSION = SIGNATURE_RESOURCE_PREFIX + ".read";

    String getPublicKey();

    JwtPayloadDto validate(String authorization ) throws TokenParserException;
}
