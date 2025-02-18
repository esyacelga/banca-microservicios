package ec.neoris.app.transacciones.servicio.acceso.datos.repository;

import ec.neoris.app.transacciones.servicio.acceso.datos.entity.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonaRepository extends CrudRepository<Persona, Integer> {
    Optional<Persona> findByIdentificacion(String identificacion);
}
