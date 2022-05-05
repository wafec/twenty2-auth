package auth.shared.core;

import auth.shared.dto.jwt.JwtHeaderDto;
import auth.shared.dto.jwt.JwtPayloadDto;
import auth.shared.exceptions.SignatureValidatorException;

public interface SignatureValidator {
    Boolean validate( JwtHeaderDto headerDto, ObjectHashParser<JwtPayloadDto> payloadHashParser,
                     String signature ) throws SignatureValidatorException;
}
