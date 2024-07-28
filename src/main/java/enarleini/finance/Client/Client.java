package enarleini.finance.Client;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;


@Entity
public record Client(
        @Id
                @GeneratedValue
        Integer id,
        @NotEmpty
        String username,
        @NotEmpty
        String password,
        @NotEmpty
        String email
) {
}
