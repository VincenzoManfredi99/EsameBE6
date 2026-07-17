package vincenzomanfredi.EsameBE6.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtenteDTO(
        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome deve essere compreso tra 2 e 30 caratteri")
        String nome,

        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra 2 e 30 caratteri")
        String cognome,

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
        String password,

        @NotEmpty(message = "Il ruolo è obbligatorio (UTENTE o ORGANIZZATORE)")
        String ruolo
) {
}
