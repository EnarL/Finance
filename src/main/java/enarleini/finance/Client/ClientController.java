package enarleini.finance.Client;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/clients")
public class ClientController {

    private final JdbcClientRepository userRepository;

    public ClientController(JdbcClientRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/findall")
    List<Client> findAll() {
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
    Client findById(@PathVariable Integer id){
        Optional<Client> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return user.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    void create(@Valid @RequestBody Client user) {
        userRepository.create(user);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Client user, @PathVariable Integer id) {
        userRepository.update(user, id);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        userRepository.delete(id);
    }




}