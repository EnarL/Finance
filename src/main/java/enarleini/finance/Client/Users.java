package enarleini.finance.Client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "Client")
@NoArgsConstructor
@AllArgsConstructor
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userID;
        @NotEmpty(message = "Username is required")
        private String username;
        @NotEmpty(message = "Password is required")
        private String password;
        @NotEmpty(message = "Email is required")
        private String email;
        private String role;






}