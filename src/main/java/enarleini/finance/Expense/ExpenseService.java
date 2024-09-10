package enarleini.finance.Expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }

    public void update(Expenses user, Long id) {
        Expenses expense = expenseRepository.findById(id).orElseThrow();
        expense.setAmount(user.getAmount());
        expense.setCategory(user.getCategory());
        expense.setDate(user.getDate());
        expenseRepository.save(expense);
    }



    public List<Expenses> findAllByUsername(String username) {
        return expenseRepository.findAllByUsername(username);
    }

    public Optional<Expenses> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public void create(Expenses user) {
        expenseRepository.save(user);
    }
}
