package ec.banca.app.persona.servicio.dominio.helpers;

import ec.banca.app.persona.servicio.dominio.dto.response.ResponseClientePersona;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;
import ec.banca.app.persona.servicio.dominio.mapper.ClienteDomainMapper;
import ec.banca.app.persona.servicio.dominio.puertos.output.IClientePersonaDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClienteQueryHelper {
    private final IClientePersonaDomainRepository clientePersonaRepository;
    private final ClienteDomainMapper clienteDomainMapper;

    public ClienteQueryHelper(IClientePersonaDomainRepository clientePersonaRepository,
                              ClienteDomainMapper clienteDomainMapper) {
        this.clientePersonaRepository = clientePersonaRepository;
        this.clienteDomainMapper = clienteDomainMapper;
    }


    public ResponseClientePersona buscarClientePorClienteId(String clienteId) throws PersonaNotFoundDomainException {
        return clienteDomainMapper.clienteDtoToRequestCliente(clientePersonaRepository.buscarClientePorClienteId(clienteId));
    }

    public ResponseClientePersona buscarClientePorIdentificacion(String identificacion) throws PersonaNotFoundDomainException {
        return clienteDomainMapper.clienteDtoToRequestCliente(clientePersonaRepository.buscarClientePorIdentificacion(identificacion));
    }


}
