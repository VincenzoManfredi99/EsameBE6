package vincenzomanfredi.EsameBE6.payloads;

import jakarta.validation.constraints.NotEmpty;

public record PasswordChangeDTO(
        @NotEmpty(message = "La vecchia password è obbligatoria!")
        String oldPassword,

        @NotEmpty(message = "La nuova password è obbligatoria!")
        String newPassword
) {
}
