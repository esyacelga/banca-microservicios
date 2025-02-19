package ec.banca.app.transacciones.servicio.dominio.validators.impl;

import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.validators.IValidacionStrategy;

public class ValidacionDebitoImpl implements IValidacionStrategy {
    @Override
    public void validarMovimiento(MovimientoAggregateRoot movimiento) {
        if (movimiento.getSaldoActual().compareTo(movimiento.getValor()) < 0) {
            throw new TransaccionDomainException("Saldo insuficiente.");
        }
    }
}
