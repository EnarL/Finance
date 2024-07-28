package enarleini.finance.Expense;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcExpenseRepository implements ExpenseRepository {
    private final JdbcClient jdbcClient;
    public JdbcExpenseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Expense> findAll() {
        return jdbcClient.sql("select * from Expense")
                .query(Expense.class)
                .list();
    }


    public Optional<Expense> findById(Integer id) {
        return jdbcClient.sql("SELECT id,username,amount,category,description,date FROM Expense WHERE id = :id" )
                .param("id", id)
                .query(Expense.class)
                .optional();
    }


    public void create(Expense users) {
        var updated = jdbcClient.sql("INSERT INTO Expense(username,amount,category,description,date) values(?,?,?,?,?)")
                .params(List.of(users.username(),users.amount(),users.category(),users.description(), users.date()))
                .update();
        Assert.state(updated == 1, "Failed to create user " + users.username());
    }


    public void update(Expense users, Integer id) {
        var updated = jdbcClient.sql("update Expense set username = ?, amount = ?, category = ?, description = ?, date = ? where id = ?")
                .params(List.of(users.username(),users.amount(),users.category(),users.description(), users.date(), id))
                .update();
        Assert.state(updated == 1, "Failed to update user " + users.username());
    }


    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from Expense where id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete user " + id);

    }


    public int count() {
        return jdbcClient.sql("select * from Expense").query().listOfRows().size();
    }


    public void saveAll(List<Expense> users) {
        users.forEach(this::create);

    }
}
