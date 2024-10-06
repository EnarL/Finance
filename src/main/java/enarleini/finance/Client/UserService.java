package enarleini.finance.Client;

import enarleini.finance.config.JWTService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
      user.setPassword(encoder.encode(user.getPassword()));
      user.setRole(Roles.USER);
      return repository.save(user);
    }

    public Map<String, String> verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token = "";
        if (authentication.isAuthenticated()) {
            token = jwtService.generateToken(user.getUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
        }
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    public List<Users> findAllClients() {
        return repository.findAll();
    }

    public Users findClientById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found."));
    }

    public Users findClientByUsername(String username) {
        return repository.findByUsername(username);
    }


    public void deleteClient(Long id) {
        repository.deleteById(id);
    }

    public void changeClientPassword(String username, String currentPassword, String newPassword) {
        Users existingClient = findClientByUsername(username);
        if (existingClient != null) {
            if (encoder.matches(currentPassword, existingClient.getPassword())) {
                existingClient.setPassword(encoder.encode(newPassword));
                repository.save(existingClient);
            } else {
                throw new RuntimeException("Current password is incorrect ");
            }
        } else {
            throw new IllegalArgumentException("Client with username " + username + " not found");
        }
    }

    public void assignRoleToUser(String username, Roles role) {
        Users user = repository.findByUsername(username);
        if (user != null) {
            user.setRole(role);
            repository.save(user);
        } else {
            throw new IllegalArgumentException("User with username " + username + " not found");
        }
    }
}