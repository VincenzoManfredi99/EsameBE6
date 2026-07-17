package vincenzomanfredi.EsameBE6.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UtenteUpdateDTO(
        @NotEmpty(message = "Il nome è obbligatorio")
        String nome,

        @NotEmpty(message = "Il cognome è obbligatorio")
        String cognome,

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email non è valida")
        String email
) {
}
