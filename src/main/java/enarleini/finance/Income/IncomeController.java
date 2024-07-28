package enarleini.finance.Income;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin
public class IncomeController {

    private final JdbcIncomeRepository userRepository;

    public IncomeController(JdbcIncomeRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/findall")
    List<Income> findAll() {
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
    Income findById(@PathVariable Integer id){
        Optional<Income> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return user.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    void create(@Valid @RequestBody Income user) {
        userRepository.create(user);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Income user, @PathVariable Integer id) {
        userRepository.update(user, id);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        userRepository.delete(id);
    }
}


