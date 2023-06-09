package epicode.JAVASPRINGPROGETTO.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import epicode.JAVASPRINGPROGETTO.entities.Dispositivo;

@Repository
public interface DispositiviRepository extends JpaRepository<Dispositivo, UUID> {

}
