package enarleini.finance.Expense;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpensesDto {

    @JsonProperty("expense_id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("category")
    private String category;

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    private String date;  // Date as a string (e.g., "2024-10-08")
}
