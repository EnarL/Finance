package enarleini.finance.Expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expenses> findAllByUsername(String username) {
        return expenseRepository.findAllByUsername(username);
    }

    public Optional<Expenses> findById(Long id) {
        return expenseRepository.findById(id);
    }
    public List<Expenses> findByUsernameAndCategory(String username, String category) {
        return expenseRepository.findByUsernameAndCategory(username, category);
    }

    public void create(Expenses user) {
        expenseRepository.save(user);
    }
    public BigDecimal sumExpensesByMonth(String username, YearMonth month) {
        List<Expenses> expenses = expenseRepository.findAllByUsername(username);
        return expenses.stream()
                .filter(expense -> YearMonth.from(expense.getDate()).equals(month))
                .map(Expenses::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public Expenses updateExpense(Long id, ExpensesDto updatedExpenseDto) {
        return expenseRepository.findById(id).map(expense -> {
            if (updatedExpenseDto.getAmount() != null) {
                expense.setAmount(updatedExpenseDto.getAmount());
            }
            if (updatedExpenseDto.getCategory() != null) {
                expense.setCategory(updatedExpenseDto.getCategory());
            }
            if (updatedExpenseDto.getDescription() != null) {
                expense.setDescription(updatedExpenseDto.getDescription());
            }
            if (updatedExpenseDto.getDate() != null) {
                try {
                    expense.setDate(LocalDate.parse(updatedExpenseDto.getDate()));
                } catch (DateTimeParseException e) {
                    throw new RuntimeException("Invalid date format: " + updatedExpenseDto.getDate());
                }
            }
            return expenseRepository.save(expense);
        }).orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }
    public List<Expenses> findByUsernameAndDateBetween(String username, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUsernameAndDateBetween(username, startDate, endDate);
    }
}