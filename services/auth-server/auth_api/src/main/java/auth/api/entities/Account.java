package auth.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "accounts" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
    private Long id;
}
