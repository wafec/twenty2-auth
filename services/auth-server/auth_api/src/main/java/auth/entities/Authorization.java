package auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authorization {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    private Date createdDate;
    private Date expiresIn;
    private String token;
    @ManyToOne( fetch = FetchType.LAZY )
    private User user;
}
