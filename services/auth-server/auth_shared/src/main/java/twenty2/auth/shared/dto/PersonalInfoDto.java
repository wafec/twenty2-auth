package twenty2.auth.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonalInfoDto {
    private String firstName;
    private String lastName;
    private String primaryEmail;
    private String secondaryEmail;
    private String primaryPhone;
    private String secondaryPhone;
    private String socialSecurityNumber;
    private String residentialAddress;
}
