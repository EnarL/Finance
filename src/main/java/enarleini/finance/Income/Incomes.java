package enarleini.finance.Income;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Data
@ToString
@Table(name = "Income")
@NoArgsConstructor
@AllArgsConstructor

public class Incomes{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotEmpty(message = "Username is required")
    String username;
    @NotNull(message = "Amount is required")
    BigDecimal amount;
    @NotEmpty(message = "Source is required")
    String source;
    LocalDate date;
}




