package enarleini.finance.Expense;

import enarleini.finance.config.AuthenticatedUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/expenses")

public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @Autowired
    AuthenticatedUserService authenticatedUserService;
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @GetMapping("/findall")
    List<Expenses> findAll(@RequestParam String username) {
        return service.findAllByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).get().getUsername() == authentication.principal.username")
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
@PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @GetMapping("/sum")
    public BigDecimal sumExpensesByMonth(@RequestParam String username, @RequestParam String month){
        return service.sumExpensesByMonth(username, YearMonth.parse(month));
    }
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @GetMapping("/search")
    public List<Expenses> findByUsernameAndDateBetween(@RequestParam String username, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return service.findByUsernameAndDateBetween(username, startDate, endDate);
    }

    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @GetMapping("/category")
    public List<Expenses> findByUsernameAndCategory(@RequestParam String username, @RequestParam String category){
        return service.findByUsernameAndCategory(username, category);
    }





}
