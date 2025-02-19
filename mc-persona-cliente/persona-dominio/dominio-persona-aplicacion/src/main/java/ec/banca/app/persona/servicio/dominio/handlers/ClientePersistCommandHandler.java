package ec.banca.app.persona.servicio.dominio.handlers;

import ec.banca.app.persona.servicio.dominio.dto.ClienteDto;
import ec.banca.app.persona.servicio.dominio.dto.request.RequestCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseCliente;
import ec.banca.app.persona.servicio.dominio.exception.PersonaConstrainViolationException;
import ec.banca.app.persona.servicio.dominio.exception.PersonaDomainException;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;
import ec.banca.app.persona.servicio.dominio.helpers.ClientePersistHelper;
import ec.banca.app.persona.servicio.dominio.puertos.output.IClientePersonaDomainRepository;
import ec.banca.app.persona.servicio.dominio.puertos.output.IClientePublisher;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ClientePersistCommandHandler {
    private final ClientePersistHelper clientePersistHelper;
    private final IClientePublisher clientePublisher;
    private final IClientePersonaDomainRepository clientePersonaRepository;

    public ClientePersistCommandHandler(ClientePersistHelper clientePersistHelper, IClientePublisher clientePublisher,
                                        IClientePersonaDomainRepository clientePersonaRepository) {
        this.clientePersistHelper = clientePersistHelper;
        this.clientePublisher = clientePublisher;
        this.clientePersonaRepository = clientePersonaRepository;
    }

    public ResponseCliente insertarCliente(RequestCliente cliente) throws PersonaConstrainViolationException {
        try {
            return clientePersistHelper.insertarCliente(cliente);
        } catch (Exception exception) {
            throw new PersonaConstrainViolationException("Error al insertar cliente con identificacion {" + cliente.getIdentificacion().toString() + "}", exception);
        }
    }

    public void elimininarCliente(UUID uuidCliente) throws PersonaNotFoundDomainException {
        clientePersistHelper.elimininarCliente(uuidCliente);
    }

    public ResponseCliente updateCliente(UUID idCliente, RequestCliente cliente) throws PersonaDomainException {
        try {
            Optional<ClienteDto> data = clientePersonaRepository.buscarClientePorClienteId(cliente.getClienteId());
            data.ifPresent(clienteDto -> {
                        log.info("Cliente con estado {" + clienteDto.getEstado() + "} *******************************");
                        if (!clienteDto.getEstado())
                            log.info("Cliente desactivado, activando cliente.........");
                            clientePublisher.desactivarCliente(cliente.getClienteId());
                    }
            );
            return clientePersistHelper.updateCliente(idCliente, cliente)
                    .map(clienteDto -> ResponseCliente.builder()
                            .uuId(clienteDto.getUuId())
                            .mensaje("Cliente actualizado correctamente")
                            .build())
                    .orElseThrow(() -> new PersonaNotFoundDomainException("Cliente con identificacion {" + idCliente.toString() + "} no encontrado"));
        } catch (PersonaNotFoundDomainException ex) {
            throw ex;
        } catch (ConstraintViolationException ex) {
            throw new PersonaConstrainViolationException("Violación de restricciones al actualizar el cliente con identificacion {" + cliente.getIdentificacion() + "}", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new PersonaConstrainViolationException("Error de integridad en la base de datos al actualizar el cliente con identificacion {" + cliente.getIdentificacion() + "}", ex);
        } catch (Exception ex) {
            throw new PersonaDomainException("Error inesperado al actualizar el cliente con identificacion {" + cliente.getIdentificacion() + "}", ex);
        }

    }
}
