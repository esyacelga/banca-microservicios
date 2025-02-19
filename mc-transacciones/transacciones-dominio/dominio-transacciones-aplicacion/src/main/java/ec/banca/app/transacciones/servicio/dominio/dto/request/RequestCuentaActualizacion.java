package ec.banca.app.transacciones.servicio.dominio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class RequestCuentaActualizacion {
    @NotNull(message = "El numero de cuenta no puede estar en nulo")
    private final String numeroCuenta;
    @NotNull(message = "El tipo de cuenta no puede estar en nulo")
    private final String tipoCuenta;
    @NotNull(message = "El saldo no puede estar en nulo")
    private final BigDecimal saldo;
    @NotNull(message = "El estado no puede ser nulo")
    private final Boolean estado;
}
