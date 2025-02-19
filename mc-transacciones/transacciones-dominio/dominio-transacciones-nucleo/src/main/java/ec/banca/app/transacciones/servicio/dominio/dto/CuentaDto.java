package ec.banca.app.transacciones.servicio.dominio.dto;

import ec.banca.app.excepcion.comun.dominio.valor.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CuentaDto {
    private final UUID uuidCuenta;
    private final String clienteId;
    private final String numeroCuenta;
    private final BigDecimal saldo;
    private final Boolean estado;
    private final TipoCuenta tipoCuenta;
}
