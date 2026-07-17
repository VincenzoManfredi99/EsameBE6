package vincenzomanfredi.EsameBE6.payloads;

import jakarta.validation.constraints.NotNull;

public record PrenotazioneDTO(
        @NotNull(message = "L'ID dell'utente è obbligatorio")
        Long utenteId,

        @NotNull(message = "L'ID dell'evento è obbligatorio")
        Long eventoId
) {
}
