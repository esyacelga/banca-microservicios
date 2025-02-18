package ec.neoris.app.transacciones.servicio.dominio.mapper;

import ec.neoris.app.excepcion.comun.dominio.valor.TipoCuenta;
import ec.neoris.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestCuenta;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestCuentaActualizacion;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.neoris.app.transacciones.servicio.dominio.entidad.MovimientoCreditoAggregateRoot;
import ec.neoris.app.transacciones.servicio.dominio.entidad.MovimientoDebitoAggregateRoot;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TransaccionDomainMapper {

    public MovimientoDebitoAggregateRoot requestMovimientoDebitoToAggregateRoot(RequestMovimiento requestMovimiento, BigDecimal saldoActual) {
        return (MovimientoDebitoAggregateRoot) MovimientoDebitoAggregateRoot.build()
                .tipoMovimiento(requestMovimiento.getTipoMovimiento())
                .saldoActual(saldoActual)
                .valor(requestMovimiento.getValor())
                .numeroCuenta(requestMovimiento.getNumeroCuenta())
                .build();
    }

    public MovimientoCreditoAggregateRoot requestMovimientoCreditoToAggregateRoot(RequestMovimiento requestMovimiento, BigDecimal saldoActual) {
        return (MovimientoCreditoAggregateRoot) MovimientoCreditoAggregateRoot.build()
                .tipoMovimiento(requestMovimiento.getTipoMovimiento())
                .saldoActual(saldoActual)
                .valor(requestMovimiento.getValor())
                .numeroCuenta(requestMovimiento.getNumeroCuenta())
                .build();
    }


    public CuentaDto requestCuentaToCuentaDto(UUID uuid, RequestCuenta requestCuenta) {
        return CuentaDto.builder()
                .uuidCuenta(uuid)
                .numeroCuenta(requestCuenta.getNumeroCuenta().toString())
                .estado(requestCuenta.getEstado())
                .clienteId(requestCuenta.getClienteId())
                .tipoCuenta(TipoCuenta.valueOf(requestCuenta.getTipoCuenta()))
                .saldo(requestCuenta.getSaldo())
                .build();
    }

    public CuentaDto requestCuentaActualizacionToCuentaDto(RequestCuentaActualizacion requestCuenta, String cliente) {
        return CuentaDto.builder()
                .numeroCuenta(requestCuenta.getNumeroCuenta().toString())
                .estado(requestCuenta.getEstado())
                .clienteId(cliente)
                .tipoCuenta(TipoCuenta.valueOf(requestCuenta.getTipoCuenta()))
                .saldo(requestCuenta.getSaldo())
                .build();
    }
}
