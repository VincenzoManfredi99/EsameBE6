package vincenzomanfredi.EsameBE6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vincenzomanfredi.EsameBE6.entities.Utente;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmail(String email);

    boolean existsByEmail(String email);
}
