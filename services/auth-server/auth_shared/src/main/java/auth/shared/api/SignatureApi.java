package auth.shared.api;

import auth.shared.dto.jwt.JwtPayloadDto;
import auth.shared.exceptions.TokenParserException;

public interface SignatureApi {
    String getPublicKey();

    JwtPayloadDto validate(String authorization ) throws TokenParserException;
}
