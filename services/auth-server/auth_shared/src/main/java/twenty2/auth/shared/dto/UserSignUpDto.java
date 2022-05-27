package twenty2.auth.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpDto {
    @NotNull
    private String name;
    @NotNull
    private String password;
}
