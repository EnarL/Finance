package enarleini.finance.Income;

import enarleini.finance.Expense.Expense;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
    List<Income> findAll(@RequestParam String username) {
        return userRepository.findAllByUsername(username);
    }
    @GetMapping("/{id}")
    Income findById(@PathVariable Integer id){
        Optional<Income> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return user.get();
    }
    @GetMapping("/sum")
    Optional<Integer> sumAllIncomesByMonth(@RequestParam String username, int month) {
        return userRepository.sumAllIncomesByMonth(username, month);
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


