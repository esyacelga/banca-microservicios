package ec.banca.app.persona.servicio.dominio.puertos.output;

import ec.banca.app.persona.servicio.dominio.dto.ClienteDto;
import ec.banca.app.persona.servicio.dominio.exception.PersonaDomainException;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;

import java.util.Optional;
import java.util.UUID;

public interface IClientePersonaDomainRepository {

    Optional<ClienteDto> buscarClientePorClienteId(String clienteId);

    Optional<ClienteDto> buscarClientePorIdentificacion(String identificacion);

    void eliminarCliente(UUID uuidCliente) throws PersonaDomainException, PersonaNotFoundDomainException;

    ClienteDto insertarCliente(ClienteDto clienteDto);

    Optional<ClienteDto> actualizarCliente(UUID uuidCliente, ClienteDto clienteDto) throws PersonaDomainException;


}
