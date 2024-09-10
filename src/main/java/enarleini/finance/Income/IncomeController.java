package enarleini.finance.Income;

import enarleini.finance.Expense.ExpenseService;
import enarleini.finance.Expense.Expenses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")

public class IncomeController {

    @Autowired
    private IncomeService service;


    @GetMapping("/findall")
    List<Incomes> findAll(@RequestParam String username) {
        return service.findAllByUsername(username);
    }


    @GetMapping("/{id}")
    Incomes findById(@PathVariable Long id) {
        Optional<Incomes> user = service.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return user.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    void create(@Valid @RequestBody Incomes user) {
        service.create(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Incomes user, @PathVariable Long id) {
        service.update(user, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
