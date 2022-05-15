package twenty2.auth.shared.core;

import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;

public interface TokenParser {
    JwtHeaderDto header();

    JwtPayloadDto payload();
}
