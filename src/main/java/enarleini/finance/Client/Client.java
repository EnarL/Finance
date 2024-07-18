package enarleini.finance.Client;

import jakarta.validation.constraints.NotEmpty;



public record Client(
        Integer id,
        @NotEmpty
        String username,
        @NotEmpty
        String password,
        @NotEmpty
        String email
) {
}
