package ec.banca.app.persona.servicio.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class ClienteDto extends PersonaDto {
    private String clienteId;
    private Boolean estado;
    private String contrasenia;
}
