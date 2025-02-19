package ec.banca.app.transacciones.servicio.dominio.entidad;

public class CuentaAhorrosAggregateRoot extends CuentaAggregateRoot {

    private CuentaAhorrosAggregateRoot(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends CuentaAggregateRoot.Builder {
        @Override
        public CuentaAhorrosAggregateRoot build() {
            return new CuentaAhorrosAggregateRoot(this);
        }
    }
}
