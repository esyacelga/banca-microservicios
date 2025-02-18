package ec.neoris.app.transacciones.servicio.dominio.entidad;

public class MovimientoCreditoAggregateRoot extends MovimientoAggregateRoot {

    public MovimientoCreditoAggregateRoot(Builder builder) {
        super(builder);
    }

    public static Builder build() {
        return new Builder();
    }

    @Override
    public void validar() {
        super.validar();
    }

    public static final class Builder extends MovimientoAggregateRoot.Builder {
        @Override
        public MovimientoCreditoAggregateRoot build() {
            return new MovimientoCreditoAggregateRoot(this);
        }
    }


}
