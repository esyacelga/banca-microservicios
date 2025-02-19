package ec.banca.app.transacciones.servicio.dominio.validators.impl;

import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.validators.IValidacionStrategy;

import java.math.BigDecimal;

public class ValidacionCreditoImpl implements IValidacionStrategy {
    @Override
    public void validarMovimiento(MovimientoAggregateRoot movimiento) {
        if (movimiento.getValor().compareTo(new BigDecimal("10000")) > 0) {
            throw new TransaccionDomainException("El crédito no puede exceder 10,000.");
        }
    }
}
