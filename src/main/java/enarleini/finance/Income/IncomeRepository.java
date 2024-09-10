package enarleini.finance.Income;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Incomes, Long> {

    List<Incomes> findAllByUsername(String username);
}

