package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository;

import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonaRepository extends CrudRepository<Persona, Integer> {
    Optional<Persona> findByIdentificacion(String identificacion);
}
