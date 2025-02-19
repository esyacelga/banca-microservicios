package ec.banca.app.transacciones.servicio.dominio.entidad;

public class MovimientoCreditoAggregateRoot extends MovimientoAggregateRoot {

    public MovimientoCreditoAggregateRoot(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void validar() {
        super.validar();
        System.out.println("***********");
    }

    public static final class Builder extends MovimientoAggregateRoot.Builder {
        @Override
        public MovimientoCreditoAggregateRoot build() {
            return new MovimientoCreditoAggregateRoot(this);
        }
    }


}
