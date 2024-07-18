package enarleini.finance.Income;


import jakarta.validation.constraints.NotEmpty;


import java.math.BigDecimal;
import java.time.LocalDate;

public record Income(
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
