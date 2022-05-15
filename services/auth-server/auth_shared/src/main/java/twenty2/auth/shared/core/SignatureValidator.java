package twenty2.auth.shared.core;

import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;
import twenty2.auth.shared.exceptions.SignatureValidatorException;

public interface SignatureValidator {
    Boolean validate( JwtHeaderDto headerDto, ObjectHashParser<JwtPayloadDto> payloadHashParser,
                     String signature ) throws SignatureValidatorException;
}
