package ec.banca.app.persona.servicio.acceso.datos.mapper;

import ec.banca.app.persona.servicio.acceso.datos.entity.Cliente;
import ec.banca.app.persona.servicio.dominio.dto.ClienteDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteDataAccesMapper {

    public Optional<ClienteDto> clienteDtoToCliente(Optional<Cliente> cliente) {
        return cliente.map(this::clienteToClienteDto);
    }

    public ClienteDto clienteToClienteDto(Cliente cliente) {
        return ClienteDto.builder()
                .uuId(cliente.getId())
                .estado(cliente.getEstado())
                .edad(cliente.getEdad())
                .genero(cliente.getGenero())
                .direccion(cliente.getDireccion())
                .identificacion(cliente.getIdentificacion())
                .nombre(cliente.getNombre())
                .telefono(cliente.getTelefono())
                .clienteId(cliente.getClienteId())
                .build();
    }

    public Cliente clienteDtoToCliente(ClienteDto clienteDto) {
        return Cliente.builder()
                .id(clienteDto.getUuId())
                .clienteId(clienteDto.getClienteId())
                .estado(clienteDto.getEstado())
                .contrasenia(clienteDto.getContrasenia())
                .telefono(clienteDto.getTelefono())
                .edad(clienteDto.getEdad())
                .genero(clienteDto.getGenero())
                .nombre(clienteDto.getNombre())
                .identificacion(clienteDto.getIdentificacion())
                .direccion(clienteDto.getDireccion())
                .build();
    }
}
