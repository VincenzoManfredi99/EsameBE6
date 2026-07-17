package vincenzomanfredi.EsameBE6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vincenzomanfredi.EsameBE6.entities.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

}
