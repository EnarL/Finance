package enarleini.finance.Client;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user)   {
        return service.verify(user);
    }
    @GetMapping("/findall")
    public List<Users> findAll() {
        return service.findAllClients();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/id/{id}")
    public Users findById(@PathVariable Long id){
        return service.findClientById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found."));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/username/{username}")
    public Users findByUsername(@PathVariable String username){
        return service.findClientByUsername(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public void create(@Valid @RequestBody Users client) {
        service.createClient(client);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Users client, @PathVariable Long id) {
        service.updateClient(client, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/change-password")
    public void changePassword(@PathVariable Long id, @RequestParam String currentPassword, @RequestParam String newPassword) {
        service.changeClientPassword(id, currentPassword, newPassword);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteClient(id);
    }

}
