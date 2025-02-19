package ec.banca.app.transacciones.servicio.dominio.dto.request;

import ec.banca.app.excepcion.comun.dominio.valor.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class RequestMovimientoActualizacion {
    private final UUID uuidMovimiento;
    @NotNull(message = "El tipoMovimiento  de cuenta no puede estar en nulo")
    private final TipoMovimiento tipoMovimiento;
    @NotNull(message = "El valor no puede estar en nulo")
    private BigDecimal valor;
}
