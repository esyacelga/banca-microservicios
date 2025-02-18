package ec.neoris.app.transacciones.servicio.dominio.entidad;

import ec.neoris.app.transacciones.servicio.dominio.exception.TransaccionDomainException;

public class MovimientoDebitoAggregateRoot extends MovimientoAggregateRoot {

    public MovimientoDebitoAggregateRoot(Builder builder) {
        super(builder);
    }

    public static MovimientoDebitoAggregateRoot.Builder build() {
        return new MovimientoDebitoAggregateRoot.Builder();
    }

    @Override
    public void validar() {
        super.validar();
        if (getSaldoActual().compareTo(getValor()) < 0) {
            throw new TransaccionDomainException("Saldo insuficiente");
        }
    }


    public static final class Builder extends MovimientoAggregateRoot.Builder {
        @Override
        public MovimientoDebitoAggregateRoot build() {
            return new MovimientoDebitoAggregateRoot(this);
        }
    }
}
