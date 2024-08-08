package enarleini.finance.Client;


import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {


    private final ClientRepository clientRepository;

    public AuthController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Client client) {
        Optional<Client> foundClient = clientRepository.findByUsername(client.username());
        if (foundClient.isPresent() && foundClient.get().password().equals(client.password())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("No such account");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Client client){
        Optional<Client> foundClient = clientRepository.findByUsername(client.username());
        if (foundClient.isEmpty()){
            clientRepository.create(client);
            return ResponseEntity.ok("Account created");
        } else {
            return ResponseEntity.status(409).body("Account already exists");
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/change/{id}")
    void changePassword(@PathVariable Integer id, @RequestParam String newPassword) {
        clientRepository.changePassword(id, newPassword);
    }
}