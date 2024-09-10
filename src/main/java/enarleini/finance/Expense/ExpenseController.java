package enarleini.finance.Expense;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")

public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @GetMapping("/findall")
    List<Expenses> findAll(@RequestParam String username) {
        return service.findAllByUsername(username);
    }


    @GetMapping("/{id}")
    Expenses findById(@PathVariable Long id){
        Optional<Expenses> user = service.findById(id);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return user.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    void create(@Valid @RequestBody Expenses user) {
        service.create(user);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Expenses user, @PathVariable Long id) {
        service.update(user, id);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }




}
