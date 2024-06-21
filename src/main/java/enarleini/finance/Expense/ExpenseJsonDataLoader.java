package enarleini.finance.Expense;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import enarleini.finance.run.Runs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class ExpenseJsonDataLoader implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final ExpenseRepository expenseRepository;

    public ExpenseJsonDataLoader(ObjectMapper objectMapper, @Qualifier("jdbcExpenseRepository") ExpenseRepository expenseRepository) {
        this.objectMapper = objectMapper;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(expenseRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/expenses.json")) {
                Expenses allExpenses = objectMapper.readValue(inputStream, Expenses.class);
                log.info("Reading {} runs from JSON data and saving to database", allExpenses.expenses().size());
                expenseRepository.saveAll(allExpenses.expenses());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }

}
