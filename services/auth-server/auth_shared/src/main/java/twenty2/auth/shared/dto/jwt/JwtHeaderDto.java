package twenty2.auth.shared.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtHeaderDto {
    private String signAlg;
    private String hashAlg;
    private String type;
}
