package ec.banca.app.persona.servicio.acceso.datos.repository;

import ec.banca.app.persona.servicio.acceso.datos.entity.Persona;

import java.util.Optional;
import java.util.UUID;

public interface IPersonaRepository {


    Persona insertarPersona(Persona persona);

    Persona actualizarPersona(Persona persona);

    void eliminarPersona(UUID uuid);

    Optional<Persona> buscarPersonaPorIdentificacion(String identificacion);

    Optional<Persona> buscarPersonaPorId(UUID id);

}
