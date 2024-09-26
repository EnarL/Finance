package enarleini.finance.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public interface ExpenseRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findAllByUsername(String username);
    List<Expenses> findByUsernameAndCategory(String username, String category);
    List<Expenses> findByUsernameAndDateBetween(String username, LocalDate startDate, LocalDate endDate);


}
