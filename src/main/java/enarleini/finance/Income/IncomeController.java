package enarleini.finance.Income;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/incomes")

public class IncomeController {

    @Autowired
    private IncomeService service;



    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @GetMapping("/findall")
    List<Incomes> findAll(@RequestParam String username) {
        return service.findAllByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
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
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @GetMapping("/sum")
    public BigDecimal sumIncomesByMonth(@RequestParam String username, @RequestParam String month){
        return service.sumIncomesByMonth(username, YearMonth.parse(month));
    }



}