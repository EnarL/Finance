package enarleini.finance.Expense;

import org.springframework.data.jdbc.repository.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {
    List<Expense> findAllByUsername(String username);

    Optional<Expense> findById(Integer id);

    void create(Expense run);

    void update(Expense run, Integer id);

    void delete(Integer id);

    int count();

    void saveAll(List<Expense> users);

    Optional<Integer> sumAllExpensesByMonth(String username, int month);



    }

