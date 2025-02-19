package ec.banca.app.persona.servicio.dominio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class RequestCliente extends RequestPersona {
    @NotNull(message = "El clienteId del cliente no puede ser nulo")
    private String clienteId;

    @NotNull(message = "La contrasenia no debe estar en null")
    private final String contrasenia;

    @NotNull(message = "El estado no debe estar en null")
    private final Boolean estado;


}
