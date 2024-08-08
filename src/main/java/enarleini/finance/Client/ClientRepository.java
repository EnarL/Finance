package enarleini.finance.Client;

import enarleini.finance.Expense.Expense;
import enarleini.finance.Income.Income;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();

    Optional<Client> findById(Integer id);

    Optional<Client> findByUsername(String username);

    void create(Client run);

    void update(Client run, Integer id);

    void delete(Integer id);

    int count();

    void changePassword(Integer id, String newPassword);

    void saveAll(List<Client> users);
}
