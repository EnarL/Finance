package enarleini.finance.Expense;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Expense(
        Integer id,
        @NotEmpty
        String username,
        BigDecimal amount,
        String category,
        String description,
        LocalDate date
) {
    public Expense {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
