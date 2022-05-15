package twenty2.auth.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table( name = "users" )
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @Column( unique = true, nullable = false )
    private String name;

    @Column( name = "passwd", nullable = false )
    private String password;

    @ToString.Exclude
    @OneToMany( mappedBy = "user", fetch = FetchType.EAGER )
    private List<Claim> claims;
}
