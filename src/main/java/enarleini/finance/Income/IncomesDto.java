package enarleini.finance.Income;

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
public class IncomesDto {
    @JsonProperty("income_id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("source")
    private String source;

    @JsonProperty("date")
    private String date;
}
