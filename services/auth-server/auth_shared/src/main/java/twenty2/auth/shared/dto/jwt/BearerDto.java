package twenty2.auth.shared.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BearerDto {
    private String content;
    private String headerString;
    private String payloadString;
    private String digitalSignatureString;
}
