package enarleini.finance.Income;

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
@Table(name = "Income")
@NoArgsConstructor
@AllArgsConstructor

public class Incomes{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotEmpty
    String username;
    BigDecimal amount;
    String source;
    LocalDate date;
}




