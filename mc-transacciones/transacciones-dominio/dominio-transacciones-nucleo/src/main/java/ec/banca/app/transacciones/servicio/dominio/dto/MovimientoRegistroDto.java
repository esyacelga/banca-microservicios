package ec.banca.app.transacciones.servicio.dominio.dto;

import ec.banca.app.excepcion.comun.dominio.valor.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class MovimientoRegistroDto {
    private final UUID uuidMovimiento;
    private final String numeroCuenta;
    private final TipoMovimiento tipoMovimiento;
    private final BigDecimal valor;
    private final BigDecimal saldo;
}
