package ec.banca.app.persona.servicio.acceso.datos.adaptador;

import ec.banca.app.persona.servicio.acceso.datos.entity.Cliente;
import ec.banca.app.persona.servicio.acceso.datos.mapper.ClienteDataAccesMapper;
import ec.banca.app.persona.servicio.acceso.datos.repository.IClienteRepository;
import ec.banca.app.persona.servicio.acceso.datos.repository.IJPAClienteRepository;
import ec.banca.app.persona.servicio.acceso.datos.repository.IPersonaRepository;
import ec.banca.app.persona.servicio.dominio.dto.ClienteDto;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;
import ec.banca.app.persona.servicio.dominio.puertos.output.IClientePersonaDomainRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ClienteAdapterRepositoryImpl implements IClientePersonaDomainRepository {
    private final IPersonaRepository personaRepository;
    private final IClienteRepository clientePersonaRepository;
    private final IJPAClienteRepository jpaClienteRepository;
    private final ClienteDataAccesMapper clienteDataAccesMapper;

    public ClienteAdapterRepositoryImpl(IPersonaRepository personaRepository,
                                        IClienteRepository clientePersonaRepository,
                                        IJPAClienteRepository jpaClienteRepository, ClienteDataAccesMapper clienteDataAccesMapper) {
        this.personaRepository = personaRepository;
        this.clientePersonaRepository = clientePersonaRepository;
        this.jpaClienteRepository = jpaClienteRepository;
        this.clienteDataAccesMapper = clienteDataAccesMapper;
    }


    @Override
    public Optional<ClienteDto> buscarClientePorClienteId(String clienteId) {
        return jpaClienteRepository.findClienteByClienteId(clienteId)
                .map(clienteDataAccesMapper::clienteToClienteDto);
    }

    @Override
    public Optional<ClienteDto> buscarClientePorIdentificacion(String identificacion) {
        return jpaClienteRepository.findClienteByIdentificacion(identificacion)
                .map(clienteDataAccesMapper::clienteToClienteDto);

    }

    @Override
    public void eliminarCliente(UUID uuidCliente) throws PersonaNotFoundDomainException {
        try {
            clientePersonaRepository.eliminarCliente(uuidCliente);
        } catch (EntityNotFoundException e) {
            throw new PersonaNotFoundDomainException("Cliente con UUID " + uuidCliente + " no encontrado.", e);
        }

    }

    @Override
    public ClienteDto insertarCliente(ClienteDto clienteDto) {
        Cliente cliente = jpaClienteRepository.save(clienteDataAccesMapper.clienteDtoToCliente(clienteDto));
        return clienteDataAccesMapper.clienteToClienteDto(cliente);

    }


    @Override
    public Optional<ClienteDto> actualizarCliente(UUID uuidCliente, ClienteDto clienteDto) throws PersonaNotFoundDomainException {
        Optional<Cliente> cl = clientePersonaRepository.actualizar(uuidCliente, clienteDataAccesMapper.clienteDtoToCliente(clienteDto));
        return clienteDataAccesMapper.clienteDtoToCliente(cl);
    }


}
