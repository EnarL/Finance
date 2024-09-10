package enarleini.finance.Income;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public void delete(Long id) {
        incomeRepository.deleteById(id);
    }

    public void update(Incomes user, Long id) {
        Incomes income = incomeRepository.findById(id).orElseThrow();
        income.setAmount(user.getAmount());
        income.setSource(user.getSource());
        income.setDate(user.getDate());
        incomeRepository.save(income);
    }

    public List<Incomes> findAllByUsername(String username) {
        return incomeRepository.findAllByUsername(username);
    }

    public Optional<Incomes> findById(Long id) {
        return incomeRepository.findById(id);
    }

    public void create(Incomes user) {
        incomeRepository.save(user);
    }
}