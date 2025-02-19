package ec.banca.app.persona.servicio.dominio.mapper;

import ec.banca.app.persona.servicio.dominio.dto.ClienteDto;
import ec.banca.app.persona.servicio.dominio.dto.request.RequestCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseClientePersona;
import ec.banca.app.persona.servicio.dominio.exception.PersonaNotFoundDomainException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ClienteDomainMapper {
    public ClienteDto requestClienteToClienteDto(RequestCliente requestCliente) {
        return ClienteDto.builder()
                .nombre(requestCliente.getNombre())
                .edad(requestCliente.getEdad())
                .clienteId(requestCliente.getClienteId())
                .genero(requestCliente.getGenero())
                .telefono(requestCliente.getTelefono())
                .direccion(requestCliente.getDireccion())
                .identificacion(requestCliente.getIdentificacion())
                .contrasenia(requestCliente.getContrasenia())
                .estado(requestCliente.getEstado())
                .build();
    }

    public ClienteDto newRequestClienteToClienteDto(RequestCliente requestCliente) {
        return ClienteDto.builder()
                .nombre(requestCliente.getNombre())
                .edad(requestCliente.getEdad())
                .clienteId(requestCliente.getClienteId())
                .genero(requestCliente.getGenero())
                .telefono(requestCliente.getTelefono())
                .direccion(requestCliente.getDireccion())
                .identificacion(requestCliente.getIdentificacion())
                .contrasenia(requestCliente.getContrasenia())
                .estado(true)
                .uuId(UUID.randomUUID())
                .build();
    }

    public ResponseCliente clienteDtoToRequestCliente(ClienteDto clienteDto) {
        return ResponseCliente.builder()
                .uuId(clienteDto.getUuId())
                .mensaje("CLIENTE REGISTRADO CORRECTAMENTE")
                .build();
    }

    public ResponseClientePersona clienteDtoToRequestCliente(Optional<ClienteDto> clienteDto) {
        return clienteDto.map(cl -> ResponseClientePersona.builder()
                        .estado(cl.getEstado())
                        .identificacion(cl.getIdentificacion())
                        .genero(cl.getGenero())
                        .direccion(cl.getDireccion())
                        .edad(cl.getEdad())
                        .nombre(cl.getNombre())
                        .telefono(cl.getTelefono())
                        .clienteId(cl.getClienteId())
                        .build())
                .orElseThrow(() -> new PersonaNotFoundDomainException("Cliente o persona no encontrada"));
    }
}
