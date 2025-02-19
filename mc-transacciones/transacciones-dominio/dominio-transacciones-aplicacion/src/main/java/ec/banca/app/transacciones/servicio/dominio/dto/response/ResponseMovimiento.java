package ec.banca.app.transacciones.servicio.dominio.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ResponseMovimiento {
    @NotNull
    private final UUID uuidMovimiento;
    @NotNull
    private final String mensaje;
}
