package enarleini.finance.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findAllByUsername(String username);
    List<Expenses> findByUsernameAndCategory(String username, String category);
    List<Expenses> findByUsernameAndDateBetween(String username, LocalDate startDate, LocalDate endDate);


}
