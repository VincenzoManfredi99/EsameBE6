package vincenzomanfredi.EsameBE6.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vincenzomanfredi.EsameBE6.entities.Evento;
import vincenzomanfredi.EsameBE6.entities.Prenotazione;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.exceptions.BadRequestException;
import vincenzomanfredi.EsameBE6.exceptions.NotFoundException;
import vincenzomanfredi.EsameBE6.payloads.PrenotazioneDTO;
import vincenzomanfredi.EsameBE6.repositories.EventoRepository;
import vincenzomanfredi.EsameBE6.repositories.PrenotazioneRepository;

import java.time.LocalDate;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoService eventoService;
    private final EventoRepository eventoRepository;
    private final UtenteService utenteService;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository,
                               EventoService eventoService,
                               EventoRepository eventoRepository,
                               UtenteService utenteService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.eventoService = eventoService;
        this.eventoRepository = eventoRepository;
        this.utenteService = utenteService;
    }

    //Save
    public Prenotazione save(PrenotazioneDTO payload) {
        Utente utente = this.utenteService.findById(payload.utenteId());
        Evento evento = this.eventoService.findById(payload.eventoId());

        if (evento.getPosti() <= 0) {
            throw new BadRequestException("Spiacenti, i posti per l'evento '" + evento.getTitolo() + "' sono esauriti!");
        }

        evento.setPosti(evento.getPosti() - 1);
        this.eventoRepository.save(evento);

        Prenotazione nuovaPrenotazione = new Prenotazione(LocalDate.now(), utente, evento);
        Prenotazione savedPrenotazione = this.prenotazioneRepository.save(nuovaPrenotazione);

        log.info("Prenotazione ID " + savedPrenotazione.getId() + " creata per l'utente " + utente.getNome() + " all'evento " + evento.getTitolo());

        return savedPrenotazione;
    }

    // GET ALL
    public Page<Prenotazione> getAll(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    // FIND BY ID
    public Prenotazione findById(long prenotazioneId) {
        return this.prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException("La prenotazione con ID " + prenotazioneId + " non è stata trovata!"));
    }

    //Delete
    public void findByIdAndDelete(long prenotazioneId) {
        Prenotazione found = this.prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException("Prenotazione con ID " + prenotazioneId + " non trovata!"));

        Evento evento = found.getEvento();

        evento.setPosti(evento.getPosti() + 1);
        this.eventoRepository.save(evento);

        this.prenotazioneRepository.delete(found);
        log.info("Prenotazione con ID " + prenotazioneId + " eliminata. Posto liberato per l'evento: " + evento.getTitolo());
    }
}
