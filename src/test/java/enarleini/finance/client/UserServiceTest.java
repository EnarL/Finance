package enarleini.finance.client;

import enarleini.finance.Client.Roles;
import enarleini.finance.Client.UserRepository;
import enarleini.finance.Client.UserService;
import enarleini.finance.Client.Users;
import enarleini.finance.config.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private UserService userService;

    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        encoder = new BCryptPasswordEncoder(10);
    }

    @Test
    void testRegister() {
        Users user = new Users();
        user.setPassword("password");
        when(repository.save(any(Users.class))).thenReturn(user);

        Users registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertTrue(encoder.matches("password", registeredUser.getPassword()));
        assertEquals(Roles.USER, registeredUser.getRole());
        verify(repository, times(1)).save(user);
    }

    @Test
    void testVerify() {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(anyString())).thenReturn("token");

        Map<String, String> response = userService.verify(user);

        assertNotNull(response);
        assertEquals("token", response.get("token"));
    }

    @Test
    void testFindClientById() {
        Users user = new Users();
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        Users foundUser = userService.findClientById(1L);

        assertNotNull(foundUser);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindClientByUsername() {
        Users user = new Users();
        when(repository.findByUsername(anyString())).thenReturn(user);

        Users foundUser = userService.findClientByUsername("testuser");

        assertNotNull(foundUser);
        verify(repository, times(1)).findByUsername("testuser");
    }

    @Test
    void testDeleteClient() {
        doNothing().when(repository).deleteById(anyLong());

        userService.deleteClient(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testChangeClientPassword() {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword(encoder.encode("oldPassword"));

        when(repository.findByUsername(anyString())).thenReturn(user);

        userService.changeClientPassword("testuser", "oldPassword", "newPassword");

        assertTrue(encoder.matches("newPassword", user.getPassword()));
        verify(repository, times(1)).save(user);
    }

    @Test
    void testAssignRoleToUser() {
        Users user = new Users();
        user.setUsername("testuser");

        when(repository.findByUsername(anyString())).thenReturn(user);

        userService.assignRoleToUser("testuser", Roles.ADMIN);

        assertEquals(Roles.ADMIN, user.getRole());
        verify(repository, times(1)).save(user);
    }
}