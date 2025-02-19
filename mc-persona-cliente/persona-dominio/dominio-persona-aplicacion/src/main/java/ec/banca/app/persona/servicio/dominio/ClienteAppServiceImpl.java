package ec.banca.app.persona.servicio.dominio;

import ec.banca.app.persona.servicio.dominio.dto.request.RequestCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseClientePersona;
import ec.banca.app.persona.servicio.dominio.exception.PersonaConstrainViolationException;
import ec.banca.app.persona.servicio.dominio.exception.PersonaDomainException;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;
import ec.banca.app.persona.servicio.dominio.handlers.ClientePersistCommandHandler;
import ec.banca.app.persona.servicio.dominio.handlers.ClienteQueryCommandHandler;
import ec.banca.app.persona.servicio.dominio.puertos.input.IClienteAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@Validated
@Service
public class ClienteAppServiceImpl implements IClienteAppService {

    private final ClientePersistCommandHandler clienteCreateCommandHandler;
    private final ClienteQueryCommandHandler clienteQueryCommandHandler;

    public ClienteAppServiceImpl(ClientePersistCommandHandler clienteCreateCommandHandler,
                                 ClienteQueryCommandHandler clienteQueryCommandHandler1) {
        this.clienteCreateCommandHandler = clienteCreateCommandHandler;
        this.clienteQueryCommandHandler = clienteQueryCommandHandler1;
    }

    @Override
    public ResponseCliente insertarCliente(RequestCliente cliente) throws PersonaConstrainViolationException {
        return clienteCreateCommandHandler.insertarCliente(cliente);
    }

    @Override
    public void elimininarCliente(UUID uuidCliente) throws PersonaNotFoundDomainException {
        clienteCreateCommandHandler.elimininarCliente(uuidCliente);
    }

    @Override
    public ResponseCliente updateCliente(UUID idCliente, RequestCliente cliente) throws PersonaDomainException {
        return clienteCreateCommandHandler.updateCliente(idCliente, cliente);
    }

    @Override
    public ResponseClientePersona buscarClientePorId(String clienteId) throws PersonaNotFoundDomainException {
        return clienteQueryCommandHandler.buscarClientePorId(clienteId);
    }

    @Override
    public ResponseClientePersona buscarClientePorIdentificacion(String identificacion) throws PersonaNotFoundDomainException {
        return clienteQueryCommandHandler.buscarClientePorIdentificacion(identificacion);
    }


}
