package enarleini.finance.Income;

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
@Table(name = "Income")
@NoArgsConstructor
@AllArgsConstructor

public class Incomes{
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    Integer id;
    @JsonProperty("username")
    @Column(name="username", nullable = false)
    @NotEmpty(message = "Username is required")
    String username;
    @JsonProperty("amount")
    @Column(name="amount", nullable = false)
    @NotNull(message = "Amount is required")
    BigDecimal amount;
    @JsonProperty("source")
    @Column(name="source", nullable = false)
    @NotEmpty(message = "Source is required")
    String source;
    @JsonProperty("date")
    @Column(name="date", nullable = false)
    LocalDate date;
}




