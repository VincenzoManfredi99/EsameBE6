package vincenzomanfredi.EsameBE6.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventoDTO(
        @NotEmpty(message = "Il titolo dell'evento è obbligatorio")
        String titolo,

        @NotEmpty(message = "La descrizione è obbligatoria")
        String descrizione,

        @NotNull(message = "La data dell'evento è obbligatoria")
        LocalDate data,

        @NotEmpty(message = "Il luogo dell'evento è obbligatorio")
        String luogo,

        @NotNull(message = "Il numero di posti disponibili è obbligatorio")
        @Min(value = 1, message = "L'evento deve avere almeno 1 posto disponibile")
        Integer postiDisponibili,

        @NotNull(message = "L'ID dell'organizzatore è obbligatorio")
        Long organizzatoreId
) {

}
