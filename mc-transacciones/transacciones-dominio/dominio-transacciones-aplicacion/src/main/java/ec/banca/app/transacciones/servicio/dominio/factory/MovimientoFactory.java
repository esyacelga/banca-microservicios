package ec.banca.app.transacciones.servicio.dominio.factory;

import ec.banca.app.excepcion.comun.dominio.valor.TipoMovimiento;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoCreditoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoDebitoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.validators.impl.ValidacionDebitoImpl;

import java.math.BigDecimal;

public class MovimientoFactory {

    public static MovimientoAggregateRoot generarMovimiento(RequestMovimiento requestMovimiento, BigDecimal saldoActual) {
        if (TipoMovimiento.CREDITO.getValue().equalsIgnoreCase(requestMovimiento.getTipoMovimiento())) {
            MovimientoAggregateRoot mov = MovimientoCreditoAggregateRoot.builder()
                    .numeroCuenta(requestMovimiento.getNumeroCuenta())
                    .tipoMovimiento(requestMovimiento.getTipoMovimiento())
                    .valor(requestMovimiento.getValor())
                    .saldoActual(saldoActual)
                    .build();
            mov.setValidacionStrategy(new ValidacionDebitoImpl());
            return mov;
        } else if (TipoMovimiento.DEBITO.getValue().equalsIgnoreCase(requestMovimiento.getTipoMovimiento())) {
            MovimientoAggregateRoot mov = MovimientoDebitoAggregateRoot.builder()
                    .numeroCuenta(requestMovimiento.getNumeroCuenta())
                    .tipoMovimiento(requestMovimiento.getTipoMovimiento())
                    .valor(requestMovimiento.getValor())
                    .saldoActual(saldoActual)
                    .build();
            mov.setValidacionStrategy(new ValidacionDebitoImpl());
            return mov;
        } else {
            throw new TransaccionDomainException("Tipo de movimiento no soportado: " + requestMovimiento.getTipoMovimiento());
        }
    }

}
