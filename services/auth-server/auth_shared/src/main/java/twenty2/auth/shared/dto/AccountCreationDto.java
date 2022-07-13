package twenty2.auth.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AccountCreationDto {
    private PersonalInfoDto personalInfo;
    private UserSignUpDto user;
}
