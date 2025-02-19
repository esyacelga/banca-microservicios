package ec.banca.app.persona.servicio.acceso.datos.repository;

import ec.banca.app.persona.servicio.acceso.datos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IJPAClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findClienteByClienteId(String clienteId);

    Optional<Cliente> findClienteByIdentificacion(String identificacion);
}
