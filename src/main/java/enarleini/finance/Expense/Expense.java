package enarleini.finance.Expense;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public record Expense(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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
