package enarleini.finance.Expense;

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
@Table(name = "Expense")
@NoArgsConstructor
@AllArgsConstructor
public class Expenses{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotEmpty(message = "Username is required")
    String username;
    @NotNull(message = "Amount is required")
    BigDecimal amount;
    @NotEmpty(message = "Category is required")
    String category;
    @NotEmpty(message = "Description is required")
    String description;
    LocalDate date;
}





