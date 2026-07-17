package vincenzomanfredi.EsameBE6.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UtenteLoginDTO(
        @NotEmpty(message = "L'email è obbligatoria!")
        @Email(message = "L'email inserita non è valida!")
        String email,
        @NotEmpty(message = "La password è obbligatoria!")
        String password
) {
}
