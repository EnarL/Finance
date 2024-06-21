package enarleini.finance.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {
    List<Expense> findAll();

    Optional<Expense> findById(Integer id);

    void create(Expense run);

    void update(Expense run, Integer id);

    void delete(Integer id);

    int count();


    void saveAll(List<Expense> users);
}
