package auth.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorizationDto {
    private Long id;
    private Date createdDate;
    private Date expiresIn;
    private String token;
}
