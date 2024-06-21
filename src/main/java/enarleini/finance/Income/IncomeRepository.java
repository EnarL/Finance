package enarleini.finance.Income;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository {
    List<Income> findAll();

    Optional<Income> findById(Integer id);

    void create(Income run);

    void update(Income run, Integer id);

    void delete(Integer id);

    int count();


    void saveAll(List<Income> users);
}