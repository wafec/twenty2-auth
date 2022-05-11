package auth.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDto {
    private Long id;
    private AccountDetailsDto details;
    private UserDto user;
}