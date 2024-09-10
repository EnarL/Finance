package enarleini.finance.Client;

import enarleini.finance.config.JWTService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;
   private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public Users register(Users user) {
      user.setPassword(encoder.encode(user.getPassword()));
      user.setRole("USER");
        return repository.save(user);
    }
    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername())  ;
        } else {
            return "fail";
        }
    }

    public List<Users> findAllClients() {
        return repository.findAll();
    }

    public Optional<Users> findClientById(Long id) {
        return repository.findById(id);
    }

    public Users findClientByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void createClient(Users client) {
        client.setPassword((client.getPassword()));
        repository.save(client);
    }

    public void updateClient(Users client, Long id) {
        Optional<Users> existingClient = findClientById(id);
        if (existingClient.isPresent()) {
            Users updatedClient = existingClient.get();
            updatedClient.setUsername(client.getUsername());
            updatedClient.setEmail(client.getEmail());
            if (client.getPassword() != null && !client.getPassword().isEmpty()) {
                updatedClient.setPassword(client.getPassword());
            }
            repository.save(updatedClient);
        } else {
            throw new IllegalArgumentException("Client with ID " + id + " not found");
        }
    }

    public void deleteClient(Long id) {
        repository.deleteById(id);
    }

    public void changeClientPassword(Long id, String currentPassword, String newPassword) {
        Optional<Users> existingClient = findClientById(id);
        if (existingClient.isPresent()) {
            Users client = existingClient.get();
            if (Objects.equals(currentPassword, client.getPassword())) {
                client.setPassword(newPassword);
                repository.save(client);
            } else {
                throw new RuntimeException("Current password is incorrect");
            }
        } else {
            throw new IllegalArgumentException("Client with ID " + id + " not found");
        }
    }
}