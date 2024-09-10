package enarleini.finance.Expense;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    String username;
    BigDecimal amount;
    String category;
    String description;
    LocalDate date;
}





