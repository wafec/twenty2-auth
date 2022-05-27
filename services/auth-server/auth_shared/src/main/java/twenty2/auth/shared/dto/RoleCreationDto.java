package twenty2.auth.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreationDto {
    private String name;
    @ToString.Exclude
    private List<Long> claimIds;
}
