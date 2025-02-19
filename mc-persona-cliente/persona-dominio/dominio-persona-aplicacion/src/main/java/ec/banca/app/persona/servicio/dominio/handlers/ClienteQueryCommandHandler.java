package ec.banca.app.persona.servicio.dominio.handlers;

import ec.banca.app.persona.servicio.dominio.dto.response.ResponseClientePersona;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;
import ec.banca.app.persona.servicio.dominio.helpers.ClienteQueryHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClienteQueryCommandHandler {
    private final ClienteQueryHelper clienteQueryHelper;

    public ClienteQueryCommandHandler(ClienteQueryHelper clienteQueryHelper) {
        this.clienteQueryHelper = clienteQueryHelper;
    }

    public ResponseClientePersona buscarClientePorId(String clienteId) throws PersonaNotFoundDomainException {
        return clienteQueryHelper.buscarClientePorClienteId(clienteId);
    }

    public ResponseClientePersona buscarClientePorIdentificacion(String identificacion) throws PersonaNotFoundDomainException {
        return clienteQueryHelper.buscarClientePorIdentificacion(identificacion);
    }
}
