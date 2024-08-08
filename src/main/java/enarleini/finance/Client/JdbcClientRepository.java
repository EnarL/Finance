package enarleini.finance.Client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRepository implements ClientRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcClientRepository.class);

    private final JdbcClient jdbcClient;

    public JdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Client> findAll() {
        return jdbcClient.sql("SELECT * FROM Client")
                .query(Client.class)
                .list();
    }

    public Optional<Client> findById(Integer id) {
        return jdbcClient.sql("SELECT id, username, password, email FROM Client WHERE id = :id")
                .param("id", id)
                .query(Client.class)
                .optional();
    }

    public Optional<Client> findByUsername(String username) {
        return jdbcClient.sql("SELECT id, username, password, email FROM Client WHERE username = :username")
                .param("username", username)
                .query(Client.class)
                .optional();
    }

    public void create(Client users) {
        var updated = jdbcClient.sql("INSERT INTO Client(username, password, email) VALUES(?, ?, ?)")
                .params(List.of(users.username(), users.password(), users.email()))
                .update();
        Assert.state(updated == 1, "Failed to create user " + users.username());
    }

    public void update(Client users, Integer id) {
        var updated = jdbcClient.sql("UPDATE Client SET username = ?, password = ?, email = ? WHERE id = ?")
                .params(List.of(users.username(), users.password(), users.email(), id))
                .update();
        Assert.state(updated == 1, "Failed to update user " + users.username());
    }

    public void changePassword(Integer id, String newPassword) {
        try {
            logger.info("Changing password for user with id: {}", id);
            var updated = jdbcClient.sql("UPDATE Client SET password = ? WHERE id =?")
                    .params(List.of(newPassword, id))
                    .update();
            Assert.state(updated == 1, "Failed to update password for user with id " + id);
        } catch (Exception e) {
            logger.error("Error changing password for user with id: {}", id, e);
            throw new RuntimeException("Error changing password", e);
        }
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM Client WHERE id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete user " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM Client").query().listOfRows().size();
    }

    public void saveAll(List<Client> users) {
        users.forEach(this::create);
    }
}