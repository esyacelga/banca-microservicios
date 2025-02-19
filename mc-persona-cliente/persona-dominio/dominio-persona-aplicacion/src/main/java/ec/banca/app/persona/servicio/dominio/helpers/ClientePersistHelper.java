package ec.banca.app.persona.servicio.dominio.helpers;

import ec.banca.app.persona.servicio.dominio.dto.ClienteDto;
import ec.banca.app.persona.servicio.dominio.dto.request.RequestCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseCliente;
import ec.banca.app.persona.servicio.dominio.exception.PersonaDomainException;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;
import ec.banca.app.persona.servicio.dominio.mapper.ClienteDomainMapper;
import ec.banca.app.persona.servicio.dominio.puertos.output.IClientePersonaDomainRepository;
import ec.banca.app.persona.servicio.dominio.puertos.output.IPersonaDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ClientePersistHelper {
    private final IClientePersonaDomainRepository clientePersonaRepository;
    private final ClienteDomainMapper clienteDomainMapper;

    public ClientePersistHelper(IClientePersonaDomainRepository clientePersonaRepository,
                                IPersonaDomainRepository personaRepositoryOut,
                                ClienteDomainMapper clienteDomainMapper) {
        this.clientePersonaRepository = clientePersonaRepository;
        this.clienteDomainMapper = clienteDomainMapper;
    }

    @Transactional
    public ResponseCliente insertarCliente(RequestCliente cliente) {
        ClienteDto cl = clientePersonaRepository.insertarCliente(clienteDomainMapper.newRequestClienteToClienteDto(cliente));
        return clienteDomainMapper.clienteDtoToRequestCliente(cl);
    }

    @Transactional
    public void elimininarCliente(UUID idCliente) throws PersonaNotFoundDomainException {
        clientePersonaRepository.eliminarCliente(idCliente);
    }


    @Transactional
    public Optional<ClienteDto> updateCliente(UUID idCliente, RequestCliente cliente) throws PersonaDomainException {
        return clientePersonaRepository.actualizarCliente(idCliente, clienteDomainMapper.requestClienteToClienteDto(cliente));
    }

}
