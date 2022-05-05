package auth.shared.core;

import auth.shared.dto.jwt.JwtHeaderDto;
import auth.shared.dto.jwt.JwtPayloadDto;

public interface TokenParser {
    JwtHeaderDto header();

    JwtPayloadDto payload();
}
