package ec.banca.app.persona.servicio.dominio.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ResponseCliente {
    @NotNull
    private final UUID uuId;
    @NotNull
    private final String mensaje;
}
