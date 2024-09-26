package enarleini.finance.Client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
        @Enumerated(EnumType.STRING)
        private Roles role;









}