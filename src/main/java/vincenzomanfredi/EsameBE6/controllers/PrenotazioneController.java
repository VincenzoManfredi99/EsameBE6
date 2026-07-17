package vincenzomanfredi.EsameBE6.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vincenzomanfredi.EsameBE6.entities.Prenotazione;
import vincenzomanfredi.EsameBE6.payloads.PrenotazioneDTO;
import vincenzomanfredi.EsameBE6.services.PrenotazioneService;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    //GET
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    public Page<Prenotazione> getAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "dataPrenotazione") String orderBy) {
        return this.prenotazioneService.getAll(page, size, orderBy);
    }

    //GET
    @GetMapping("/{prenotazioneId}")
    public Prenotazione getById(@PathVariable long prenotazioneId) {
        return this.prenotazioneService.findById(prenotazioneId);
    }

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione create(@RequestBody @Validated PrenotazioneDTO body) {
        return this.prenotazioneService.save(body);
    }

    // DELETE
    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long prenotazioneId) {
        this.prenotazioneService.findByIdAndDelete(prenotazioneId);
    }
}
