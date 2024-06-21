package enarleini.finance.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();

    Optional<Client> findById(Integer id);

    void create(Client run);

    void update(Client run, Integer id);

    void delete(Integer id);

    int count();


    void saveAll(List<Client> users);
}
