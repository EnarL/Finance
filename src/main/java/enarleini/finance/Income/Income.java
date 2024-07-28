package enarleini.finance.Income;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;


import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public record Income(
        @Id
        @GeneratedValue
        Integer id,
        @NotEmpty
        String username,
        BigDecimal amount,
        String source,
        LocalDate date
)
{
    public Income{
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
