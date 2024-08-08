package enarleini.finance.Income;




import enarleini.finance.Expense.Expense;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIncomeRepository implements IncomeRepository {
    private final JdbcClient jdbcClient;
    public JdbcIncomeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Income> findAllByUsername(String username) {
        return jdbcClient.sql("SELECT * FROM Income WHERE username = :username")
                .param("username", username)
                .query(Income.class)
                .list();
    }
    public Optional<Integer> sumAllIncomesByMonth(String username,int month) {
        try {
            String sql = "SELECT SUM(amount) FROM Income WHERE username = :username AND EXTRACT(MONTH FROM date) = :month";


            Integer result = jdbcClient.sql(sql)
                    .param("username", username)
                    .param("month", month)
                    .query(Integer.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(0); // default to 0 if no result

            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public Optional<Income> findById(Integer id) {
        return jdbcClient.sql("SELECT id,username,amount,source,date FROM Income WHERE id = :id" )
                .param("id", id)
                .query(Income.class)
                .optional();
    }


    public void create(Income users) {
        var updated = jdbcClient.sql("INSERT INTO Income(username,amount,source,date) values(?,?,?,?)")
                .params(List.of(users.username(),users.amount(),users.source(), users.date()))
                .update();
        Assert.state(updated == 1, "Failed to create user " + users.username());
    }


    public void update(Income users, Integer id) {
        var updated = jdbcClient.sql("update Income set username = ?, amount = ?, source = ?, date = ? where id = ?")
                .params(List.of(users.username(),users.amount(),users.source(), users.date(), id))
                .update();
        Assert.state(updated == 1, "Failed to update user " + users.username());
    }


    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from Income where id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete user " + id);

    }

    public int count() {
        return jdbcClient.sql("select * from Income").query().listOfRows().size();
    }


    public void saveAll(List<Income> users) {
        users.forEach(this::create);

    }
}

