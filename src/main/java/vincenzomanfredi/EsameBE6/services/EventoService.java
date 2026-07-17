package vincenzomanfredi.EsameBE6.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vincenzomanfredi.EsameBE6.entities.Evento;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.exceptions.BadRequestException;
import vincenzomanfredi.EsameBE6.exceptions.NotFoundException;
import vincenzomanfredi.EsameBE6.payloads.EventoDTO;
import vincenzomanfredi.EsameBE6.repositories.EventoRepository;

@Service
@Slf4j
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UtenteService utenteService;

    public EventoService(EventoRepository eventoRepository, UtenteService utenteService) {
        this.eventoRepository = eventoRepository;
        this.utenteService = utenteService;
    }

    //SAVE
    public Evento save(EventoDTO payload, Utente organizzatore) {

        Evento nuovoEvento = new Evento(
                payload.titolo(),
                payload.descrizione(),
                payload.data(),
                payload.luogo(),
                payload.postiDisponibili(),
                organizzatore
        );

        Evento savedEvento = this.eventoRepository.save(nuovoEvento);
        log.info("Evento '" + savedEvento.getTitolo() + "' (ID: " + savedEvento.getId() + ") salvato con successo!");

        return savedEvento;
    }

    //GET ALL
    public Page<Evento> getAll(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.eventoRepository.findAll(pageable);
    }

    //FIND BY ID
    public Evento findById(long eventoId) {
        return this.eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("L'evento con ID " + eventoId + " non è stato trovato!"));
    }

    //UPDATE
    public Evento findByIdAndUpdate(long eventoId, EventoDTO payload, Utente organizzatore) {
        Evento found = this.findById(eventoId);

        if (found.getOrganizzatore().getId() != organizzatore.getId()) {
            throw new BadRequestException("Non sei autorizzato a modificare questo evento, non sei l'organizzatore!");
        }

        found.setTitolo(payload.titolo());
        found.setDescrizione(payload.descrizione());
        found.setData(payload.data());
        found.setLuogo(payload.luogo());
        found.setPosti(payload.postiDisponibili());

        return this.eventoRepository.save(found);
    }

    //DELETE
    public void findByIdAndDelete(long eventoId, Utente organizzatore) {
        Evento found = this.findById(eventoId);

        if (found.getOrganizzatore().getId() != organizzatore.getId()) {
            throw new BadRequestException("Non sei autorizzato a eliminare questo evento, non sei l'organizzatore!");
        }

        this.eventoRepository.delete(found);
        log.info("Evento con ID " + eventoId + " eliminato.");
    }
}
