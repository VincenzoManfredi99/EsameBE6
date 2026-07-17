package vincenzomanfredi.EsameBE6.services;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import vincenzomanfredi.EsameBE6.entities.Ruolo;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.payloads.UtenteDTO;
import vincenzomanfredi.EsameBE6.repositories.UtenteRepository;

public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Ci serve per hashare la password in registrazione

    // 1. Metodo per salvare un nuovo utente (Registrazione)
    public Utente save(UtenteDTO body) {
        // Controlliamo se l'email è già in uso
        if (utenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        }

        // Convertiamo la stringa del ruolo del DTO nel nostro Enum Ruolo
        Ruolo ruoloScelto;
        try {
            ruoloScelto = Ruolo.valueOf(body.ruolo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Ruolo non valido! Scegli tra UTENTE_NORMALE o ORGANIZZATORE_EVENTI.");
        }

        // Creiamo la nuova entità Utente cifrando la password prima del salvataggio
        Utente nuovoUtente = new Utente(
                body.nome(),
                body.cognome(),
                body.email(),
                passwordEncoder.encode(body.password()), // Hashing della password
                ruoloScelto
        );

        return utenteRepository.save(nuovoUtente);
    }

    // 2. Metodo di ricerca per ID (quello fondamentale che servirà al TokenFilter!)
    public Utente findById(long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente con ID " + id + " non trovato!"));
    }
}
