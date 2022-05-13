package auth.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountCreationDto {
    private PersonalInfoDto personalInfo;
    private UserSignUpDto user;
}
