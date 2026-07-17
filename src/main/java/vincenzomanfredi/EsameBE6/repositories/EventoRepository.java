package vincenzomanfredi.EsameBE6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vincenzomanfredi.EsameBE6.entities.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}
