package ec.banca.app.transacciones.servicio.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MovientoReporte {
    private final LocalDateTime fecha;
    private final String cliente;
    private final String numeroCuenta;
    private final String tipoCuenta;
    private final BigDecimal saldoInicial;
    private final Boolean estado;
    private final BigDecimal movimiento;
    private final BigDecimal saldoDisponible;
}
