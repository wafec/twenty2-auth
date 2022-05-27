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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table( name = "roles" )
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( unique = true, nullable = false )
    private String name;

    @ToString.Exclude
    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable( name = "role_claims",
            joinColumns = @JoinColumn( name = "role_id" ), inverseJoinColumns = @JoinColumn( name = "claim_id" ) )
    private List<Claim> claims;
}
