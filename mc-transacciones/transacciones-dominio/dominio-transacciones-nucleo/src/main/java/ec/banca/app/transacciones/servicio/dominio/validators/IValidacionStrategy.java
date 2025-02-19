package ec.banca.app.transacciones.servicio.dominio.validators;

import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;

public interface IValidacionStrategy {
    void validarMovimiento(MovimientoAggregateRoot movimiento);
}
