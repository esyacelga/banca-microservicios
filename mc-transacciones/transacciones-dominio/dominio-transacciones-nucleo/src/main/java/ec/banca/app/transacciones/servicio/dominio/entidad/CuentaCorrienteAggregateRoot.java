package ec.banca.app.transacciones.servicio.dominio.entidad;

public class CuentaCorrienteAggregateRoot extends CuentaAggregateRoot {

    private CuentaCorrienteAggregateRoot(Builder builder) {
        super(builder);

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends CuentaAggregateRoot.Builder {
        @Override
        public CuentaCorrienteAggregateRoot build() {
            return new CuentaCorrienteAggregateRoot(this);
        }
    }

    @Override
    public CuentaAhorrosAggregateRoot clone() {
        return (CuentaAhorrosAggregateRoot) super.clone();
    }
}

