package vincenzomanfredi.EsameBE6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vincenzomanfredi.EsameBE6.entities.Evento;
import vincenzomanfredi.EsameBE6.entities.Prenotazione;
import vincenzomanfredi.EsameBE6.entities.Utente;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsByUtenteAndEvento(Utente utente, Evento evento);

    List<Prenotazione> findByUtente(Utente utente);
}
