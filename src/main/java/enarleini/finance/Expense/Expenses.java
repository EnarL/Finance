package enarleini.finance.Expense;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("expense_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    Integer id;
    @JsonProperty("username")
    @Column(name="username", nullable = false)
    String username;
    @JsonProperty("amount")
    @Column(name="amount", nullable = false)
    BigDecimal amount;
    @JsonProperty("category")
    @Column(name="category", nullable = false)
    String category;
    @JsonProperty("description")
    @Column(name="description", nullable = false)
    String description;
    @JsonProperty("date")
    @Column(name="date", nullable = false)
    LocalDate date;
}





