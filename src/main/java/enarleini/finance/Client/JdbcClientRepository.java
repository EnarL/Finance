package enarleini.finance.Client;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRepository implements ClientRepository {
    private final JdbcClient jdbcClient;
    public JdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Client> findAll() {
        return jdbcClient.sql("select * from Client")
                .query(Client.class)
                .list();
    }


    public Optional<Client> findById(Integer id) {
        return jdbcClient.sql("SELECT id,username,password,email FROM Client WHERE id = :id" )
                .param("id", id)
                .query(Client.class)
                .optional();
    }


    public void create(Client users) {
        var updated = jdbcClient.sql("INSERT INTO Client(id,username,password,email) values(?,?,?,?)")
                .params(List.of(users.id(),users.username(),users.password(),users.email()))
                .update();
        Assert.state(updated == 1, "Failed to create user " + users.username());
    }


    public void update(Client users, Integer id) {
        var updated = jdbcClient.sql("update client set username = ?, password = ?, email = ? where id = ?")
                .params(List.of(users.username(),users.password(),users.email(), id))
                .update();
        Assert.state(updated == 1, "Failed to update user " + users.username());
    }


    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from client where id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete user " + id);

    }


    public int count() {
        return jdbcClient.sql("select * from client").query().listOfRows().size();
    }


    public void saveAll(List<Client> users) {
        users.forEach(this::create);

    }
}