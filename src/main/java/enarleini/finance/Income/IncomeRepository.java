package enarleini.finance.Income;

import enarleini.finance.Expense.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository {
    List<Income> findAllByUsername(String username);

    Optional<Income> findById(Integer id);

    void create(Income run);

    void update(Income run, Integer id);

    void delete(Integer id);

    int count();
    Optional<Integer> sumAllIncomesByMonth(String username, int month);

    void saveAll(List<Income> users);
}