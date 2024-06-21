package enarleini.finance.Expense;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final JdbcExpenseRepository userRepository;

    public ExpenseController(JdbcExpenseRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/findall")
    List<Expense> findAll() {
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
    Expense findById(@PathVariable Integer id){
        Optional<Expense> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return user.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void create(@Valid @RequestBody Expense user) {
        userRepository.create(user);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Expense user, @PathVariable Integer id) {
        userRepository.update(user, id);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        userRepository.delete(id);
    }




}
