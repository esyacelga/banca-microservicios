package ec.banca.app.persona.servicio.acceso.datos.repository.impl;

import ec.banca.app.persona.servicio.acceso.datos.entity.Cliente;
import ec.banca.app.persona.servicio.acceso.datos.repository.IClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ClienteRepositoryImpl implements IClienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cliente> buscarPorId(UUID uuid) {
        return Optional.ofNullable(entityManager.find(Cliente.class, uuid));
    }

    @Override
    public Optional<Cliente> actualizar(UUID uuid, Cliente cliente) throws EntityNotFoundException {
        Cliente clienteExistente = entityManager.find(Cliente.class, uuid);
        if (clienteExistente == null) {
            return Optional.empty();
        }
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setGenero(cliente.getGenero());
        clienteExistente.setEdad(cliente.getEdad());
        clienteExistente.setClienteId(cliente.getClienteId());
        clienteExistente.setIdentificacion(cliente.getIdentificacion());
        clienteExistente.setDireccion(cliente.getDireccion());
        clienteExistente.setTelefono(cliente.getTelefono());
        clienteExistente.setContrasenia(cliente.getContrasenia());
        clienteExistente.setEstado(cliente.getEstado());
        Cliente clienteActualizado = entityManager.merge(clienteExistente);
        return Optional.of(clienteActualizado);
    }

    @Override
    public Cliente insertarCliente(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Override
    public void eliminarCliente(UUID clienteId) throws EntityNotFoundException {
        Cliente cliente = entityManager.find(Cliente.class, clienteId);
        if (cliente == null) {
            throw new EntityNotFoundException("El recurso solicitado no existe");
        }
        entityManager.remove(cliente);
    }

    @Override
    public void eliminarClientePorIdentificacion(String identificacion) {
        try {
            Cliente cliente = entityManager.createQuery(
                            "SELECT c FROM Cliente c WHERE c.identificacion = :identificacion", Cliente.class)
                    .setParameter("identificacion", identificacion)
                    .getSingleResult();

            entityManager.remove(cliente);
        } catch (NoResultException e) {
            throw new EntityNotFoundException("No se encontró un cliente con la identificación: " + identificacion);
        }
    }
}