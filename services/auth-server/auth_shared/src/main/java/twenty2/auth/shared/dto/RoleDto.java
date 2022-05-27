package twenty2.auth.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDto {
    private Long id;
    private String name;
    @ToString.Exclude
    private List<ClaimDto> claims;
}
