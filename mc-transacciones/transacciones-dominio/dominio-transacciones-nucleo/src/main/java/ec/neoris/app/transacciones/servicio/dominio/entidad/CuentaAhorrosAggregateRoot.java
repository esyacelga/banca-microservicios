package ec.neoris.app.transacciones.servicio.dominio.entidad;

public class CuentaAhorrosAggregateRoot extends CuentaAggregateRoot {

    private CuentaAhorrosAggregateRoot(Builder builder) {
        super(builder);
    }

    public static CuentaCorrienteAggregateRoot.Builder builder() {
        return new CuentaCorrienteAggregateRoot.Builder();
    }

    public static final class Builder extends CuentaAggregateRoot.Builder {
        @Override
        public CuentaAhorrosAggregateRoot build() {
            return new CuentaAhorrosAggregateRoot(this);
        }
    }
}
