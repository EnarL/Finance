package enarleini.finance.Income;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class IncomeJsonDataLoader implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final IncomeRepository incomeRepository;

    public IncomeJsonDataLoader(ObjectMapper objectMapper, @Qualifier("jdbcIncomeRepository") IncomeRepository incomeRepository) {
        this.objectMapper = objectMapper;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(incomeRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/incomes.json")) {
                Incomes allIncomes = objectMapper.readValue(inputStream, Incomes.class);
                log.info("Reading {} runs from JSON data and saving to database", allIncomes.incomes().size());
                incomeRepository.saveAll(allIncomes.incomes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }

}