package ec.neoris.app.transacciones.servicio.dominio.validators;

import ec.neoris.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;

public interface IValidacionStrategy {
    void validarMovimiento(MovimientoAggregateRoot movimiento);
}
