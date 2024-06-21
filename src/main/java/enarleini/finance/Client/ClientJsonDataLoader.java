package enarleini.finance.Client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import enarleini.finance.run.Runs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class ClientJsonDataLoader implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final ClientRepository clientRepository;

    public ClientJsonDataLoader(ObjectMapper objectMapper, @Qualifier("jdbcClientRepository") ClientRepository clientRepository) {
        this.objectMapper = objectMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(clientRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/clients.json")) {
                Clients allClients = objectMapper.readValue(inputStream, Clients.class);
                log.info("Reading {} runs from JSON data and saving to database", allClients.clients().size());
                clientRepository.saveAll(allClients.clients());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }

}
