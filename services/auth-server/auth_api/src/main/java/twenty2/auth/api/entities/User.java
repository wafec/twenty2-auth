package twenty2.auth.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @ManyToMany
    @JoinTable( name = "user_claims",
            joinColumns = @JoinColumn( name = "user_id" ), inverseJoinColumns = @JoinColumn( name = "claim_id" ) )
    @Fetch( FetchMode.SUBSELECT )
    private List<Claim> claims;

    @ToString.Exclude
    @ManyToMany
    @JoinTable( name = "user_roles",
            joinColumns = @JoinColumn( name = "user_id" ), inverseJoinColumns = @JoinColumn( name = "role_id" ) )
    @Fetch( FetchMode.SUBSELECT )
    private List<Role> roles;
}
