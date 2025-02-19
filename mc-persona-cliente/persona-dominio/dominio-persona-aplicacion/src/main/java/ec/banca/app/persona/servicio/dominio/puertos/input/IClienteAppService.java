package ec.banca.app.persona.servicio.dominio.puertos.input;

import ec.banca.app.persona.servicio.dominio.dto.request.RequestCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseClientePersona;
import ec.banca.app.persona.servicio.dominio.exception.PersonaConstrainViolationException;
import ec.banca.app.persona.servicio.dominio.exception.PersonaDomainException;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;
import jakarta.validation.Valid;

import java.util.UUID;

public interface IClienteAppService {
    ResponseCliente insertarCliente(@Valid RequestCliente cliente) throws PersonaConstrainViolationException;

    ResponseCliente updateCliente(@Valid UUID idCliente, @Valid RequestCliente cliente) throws PersonaDomainException;

    ResponseClientePersona buscarClientePorId(@Valid String idCliente) throws PersonaNotFoundDomainException;

    ResponseClientePersona buscarClientePorIdentificacion(@Valid String identificacion) throws PersonaNotFoundDomainException;

    void elimininarCliente(@Valid UUID uuidCliente) throws PersonaDomainException, PersonaNotFoundDomainException;

}
