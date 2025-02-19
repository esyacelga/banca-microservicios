package ec.banca.app.transacciones.servicio.dominio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class RequestMovimiento {
    @NotNull(message = "El numero de cuenta no puede estar en nulo")
    private final String numeroCuenta;
    @NotNull(message = "El tipo de movimiento no puede estar en nulo")
    private final String tipoMovimiento;
    @NotNull(message = "El valor del movimiento no puede estar en nulo")
    private BigDecimal valor;
}
