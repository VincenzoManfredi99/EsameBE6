package vincenzomanfredi.EsameBE6.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vincenzomanfredi.EsameBE6.entities.Ruolo;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.exceptions.BadRequestException;
import vincenzomanfredi.EsameBE6.exceptions.NotFoundException;
import vincenzomanfredi.EsameBE6.payloads.UtenteDTO;
import vincenzomanfredi.EsameBE6.repositories.UtenteRepository;

@Service
@Slf4j
public class UtenteService {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //SAVE
    public Utente save(UtenteDTO payload) {
        if (this.utenteRepository.existsByEmail(payload.email())) {
            throw new BadRequestException("L'indirizzo email " + payload.email() + " è già utilizzato!");
        }

        Ruolo ruoloScelto;
        try {
            ruoloScelto = Ruolo.valueOf(payload.ruolo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Ruolo non valido! Scegli tra UTENTE_NORMALE o ORGANIZZATORE_EVENTI.");
        }

        Utente newUser = new Utente(
                payload.nome(),
                payload.cognome(),
                payload.email(),
                passwordEncoder.encode(payload.password()),
                ruoloScelto
        );

        Utente savedUser = this.utenteRepository.save(newUser);

        log.info("Utente " + savedUser.getId() + " salvato");

        return savedUser;
    }

    //GET ALL
    public Page<Utente> getAll(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.utenteRepository.findAll(pageable);
    }

    //FIND BY ID
    public Utente findById(long utenteId) {
        return this.utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("L'utente con ID " + utenteId + " non è stato trovato!"));
    }

    //DELETE
    public void findByIdAndDelete(long utenteId) {
        Utente found = this.findById(utenteId);
        this.utenteRepository.delete(found);
    }

    //FIND BY EMAIL
    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }

}
