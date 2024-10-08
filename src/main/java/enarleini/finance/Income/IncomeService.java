package enarleini.finance.Income;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public void delete(Long id) {
        incomeRepository.deleteById(id);
    }

    public List<Incomes> findAllByUsername(String username) {
        return incomeRepository.findAllByUsername(username);
    }

    public Optional<Incomes> findById(Long id) {
        return incomeRepository.findById(id);
    }
    public Incomes updateIncome (Long id, IncomesDto updatedIncomeDto) {
        return incomeRepository.findById(id).map(expense -> {
            if (updatedIncomeDto.getAmount() != null) {
                expense.setAmount(updatedIncomeDto.getAmount());
            }
            if (updatedIncomeDto.getSource() != null) {
                expense.setSource(updatedIncomeDto.getSource());
            }
            if (updatedIncomeDto.getDate() != null) {
                try {
                    expense.setDate(LocalDate.parse(updatedIncomeDto.getDate()));
                } catch (DateTimeParseException e) {
                    throw new RuntimeException("Invalid date format: " + updatedIncomeDto.getDate());
                }
            }
            return incomeRepository.save(expense);
        }).orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }
    public void create(Incomes user) {
        incomeRepository.save(user);
    }
    public BigDecimal sumIncomesByMonth(String username, YearMonth month) {
        List<Incomes> incomes = incomeRepository.findAllByUsername(username);
        return incomes.stream()
                .filter(expense -> YearMonth.from(expense.getDate()).equals(month))
                .map(Incomes::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}