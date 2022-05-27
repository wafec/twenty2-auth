package twenty2.auth.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "accounts" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "first_name" )
    private String firstName;

    @Column( name = "last_name" )
    private String lastName;

    @Column ( name = "primary_email" )
    private String primaryEmail;

    @Column( name = "secondary_email" )
    private String secondaryEmail;

    @Column( name = "primary_phone" )
    private String primaryPhone;

    @Column( name = "secondary_phone" )
    private String secondaryPhone;

    @Column( name = "social_security_number" )
    private String socialSecurityNumber;

    @Column( name = "residential_address" )
    private String residentialAddress;

    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false )
    @ToString.Exclude
    private User user;
}
