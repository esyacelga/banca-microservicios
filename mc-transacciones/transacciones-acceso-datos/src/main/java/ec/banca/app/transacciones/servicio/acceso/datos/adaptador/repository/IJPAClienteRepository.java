package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository;

import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IJPAClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findClienteByClienteId(String clienteId);

    Optional<Cliente> findClienteByIdentificacion(String identificacion);
}
