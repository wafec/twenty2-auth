package twenty2.auth.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table( name = "claims" )
public class Claim {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    private String value;
    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false )
    private User user;
}
