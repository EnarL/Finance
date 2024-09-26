package enarleini.finance.Income;


import enarleini.finance.Expense.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Incomes, Long> {

    List<Incomes> findAllByUsername(String username);

}

