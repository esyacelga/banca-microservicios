package ec.banca.app.persona.servicio.acceso.datos.repository.impl;

import ec.banca.app.persona.servicio.acceso.datos.entity.Persona;
import ec.banca.app.persona.servicio.acceso.datos.repository.IPersonaRepository;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PersonaRepositoryImpl implements IPersonaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Persona insertarPersona(Persona persona) {
        entityManager.persist(persona);
        return persona;
    }

    @Override
    public Persona actualizarPersona(Persona persona) {
        Persona personaExistente = entityManager.find(Persona.class, persona.getId());
        if (personaExistente == null) {
            throw new EntityNotFoundException("Persona con ID " + persona.getId() + " no encontrada.");
        }

        personaExistente.setNombre(persona.getNombre());
        personaExistente.setDireccion(persona.getDireccion());
        personaExistente.setEdad(persona.getEdad());
        personaExistente.setGenero(persona.getGenero());
        personaExistente.setTelefono(persona.getTelefono());

        return entityManager.merge(personaExistente);
    }

    @Override
    public void eliminarPersona(UUID uuid) {
        Persona persona = entityManager.find(Persona.class, uuid);
        if (persona != null) {
            entityManager.remove(persona);
        }
    }

    @Override
    public Optional<Persona> buscarPersonaPorIdentificacion(String identificacion) {
        try {
            TypedQuery<Persona> query = entityManager.createQuery(
                    "SELECT p FROM Persona p WHERE p.identificacion = :identificacion", Persona.class);
            query.setParameter("identificacion", identificacion);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Persona> buscarPersonaPorId(UUID id) {
        return Optional.ofNullable(entityManager.find(Persona.class, id));
    }
}