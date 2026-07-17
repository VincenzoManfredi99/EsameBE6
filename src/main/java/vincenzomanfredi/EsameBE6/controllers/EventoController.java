package vincenzomanfredi.EsameBE6.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vincenzomanfredi.EsameBE6.entities.Evento;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.payloads.EventoDTO;
import vincenzomanfredi.EsameBE6.services.EventoService;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // GET
    @GetMapping
    public Page<Evento> getAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "data") String orderBy) {
        return this.eventoService.getAll(page, size, orderBy);
    }

    // GET
    @GetMapping("/{eventoId}")
    public Evento getById(@PathVariable long eventoId) {
        return this.eventoService.findById(eventoId);
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Evento create(@RequestBody @Validated EventoDTO body, @AuthenticationPrincipal Utente organizzatore) {
        // Passiamo l'organizzatore loggato in modo che l'evento creato abbia il riferimento a lui
        return this.eventoService.save(body, organizzatore);
    }

    // PUT
    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Evento update(@PathVariable long eventoId, @RequestBody @Validated EventoDTO body, @AuthenticationPrincipal Utente organizzatore) {
        return this.eventoService.findByIdAndUpdate(eventoId, body, organizzatore);
    }

    // DELETE
    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public void delete(@PathVariable long eventoId, @AuthenticationPrincipal Utente organizzatore) {
        this.eventoService.findByIdAndDelete(eventoId, organizzatore);
    }
}
